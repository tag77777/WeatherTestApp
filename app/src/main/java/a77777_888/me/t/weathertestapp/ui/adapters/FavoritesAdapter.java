package a77777_888.me.t.weathertestapp.ui.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import a77777_888.me.t.weathertestapp.data.local.FavoriteRoomEntity;
import a77777_888.me.t.weathertestapp.databinding.FavoritesItemBinding;
import a77777_888.me.t.weathertestapp.model.Place;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.Holder> {
    @NonNull private List<FavoriteRoomEntity> list;
    @NonNull private final FavoritesHandler favoritesHandler;

    public FavoritesAdapter(@NonNull List<FavoriteRoomEntity> entities, @NonNull FavoritesHandler favoritesHandler) {
        this.list = entities; this.favoritesHandler = favoritesHandler;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setList(List<FavoriteRoomEntity> newList) {
        FavoritesDiffCallback diffCallback = new FavoritesDiffCallback(list, newList);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
        this.list = newList;
        diffResult.dispatchUpdatesTo(this);
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        FavoritesItemBinding binding = FavoritesItemBinding.inflate(inflater, parent, false);
        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        FavoriteRoomEntity entity = list.get(position);
        holder.binding.placeTextView.setText(list.get(position).toString());

        holder.binding.removeButton.setOnClickListener(
            v -> {
                int actualPosition = holder.getAbsoluteAdapterPosition();
                FavoriteRoomEntity removedEntity = list.get(actualPosition);
                favoritesHandler.removeFavorite(removedEntity);
            }
        );

        holder.binding.getRoot().setOnClickListener(
            v -> favoritesHandler.publishFavorite(new Place(entity))
        );
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class Holder extends RecyclerView.ViewHolder {
        private final FavoritesItemBinding binding;

        public Holder(@NonNull FavoritesItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public static class FavoritesDiffCallback extends DiffUtil.Callback {
        private final List<FavoriteRoomEntity> oldList;
        private final List<FavoriteRoomEntity> newList;

        public FavoritesDiffCallback(List<FavoriteRoomEntity> oldList, List<FavoriteRoomEntity> newList) {
            this.oldList = oldList; this.newList = newList;
        }

        @Override
        public int getOldListSize() {
            return oldList.size();
        }

        @Override
        public int getNewListSize() {
            return newList.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return oldList.get(oldItemPosition).getCountryCode().equals(newList.get(newItemPosition).getCountryCode());
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            FavoriteRoomEntity oldItem = oldList.get(oldItemPosition);
            FavoriteRoomEntity newItem = newList.get(newItemPosition);

            return oldItem.getCityName().equals(newItem.getCityName()) &&
                oldItem.getCountryCode().equals(newItem.getCountryCode());
        }
    }

    public interface FavoritesHandler {
        void publishFavorite(Place place);
        void removeFavorite(FavoriteRoomEntity entity);
    }
}
