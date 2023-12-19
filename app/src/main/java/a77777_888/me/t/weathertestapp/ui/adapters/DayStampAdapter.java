package a77777_888.me.t.weathertestapp.ui.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import a77777_888.me.t.weathertestapp.databinding.DayStampItemBinding;
import a77777_888.me.t.weathertestapp.model.UiDayStamp;

public class DayStampAdapter extends RecyclerView.Adapter<DayStampAdapter.Holder> {

   @NonNull private List<UiDayStamp> list;
   @NonNull private final Publisher publisher;
   private int selectedPosition = 0;

   public DayStampAdapter(@NonNull List<UiDayStamp> list, @NonNull Publisher publisher) {
      this.publisher = publisher;
      this.list = list;
   }

   @SuppressLint("NotifyDataSetChanged")
   public void setList(List<UiDayStamp> list) {
      this.list = list;
      notifyDataSetChanged();
   }

   @NonNull
   @Override
   public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      LayoutInflater inflater = LayoutInflater.from(parent.getContext());
      DayStampItemBinding binding = DayStampItemBinding.inflate(inflater, parent, false);
      return new Holder(binding);
   }

   @Override
   public void onBindViewHolder(@NonNull Holder holder, int position) {
      holder.binding.dateTextView.setSelected(holder.getAbsoluteAdapterPosition() == selectedPosition);
      holder.binding.temperatureTextView.setSelected(holder.getAbsoluteAdapterPosition() == selectedPosition);
      if (!holder.binding.dateTextView.isSelected()) {
        holder.itemView.setScaleX(0.9f);  holder.itemView.setScaleY(0.9f);
      } else {
         holder.itemView.setScaleX(1f); holder.itemView.setScaleY(1f);
      }

      UiDayStamp item = list.get(position);

      holder.binding.dateTextView.setText(item.getDateString());
      holder.binding.weatherIconImageView.setImageResource(item.getIconRes());
      holder.binding.temperatureTextView.setText(item.getTemperature());
      holder.binding.descriptionTextView.setText(item.getDescription());

      holder.binding.getRoot().setOnClickListener(
          v -> {
             int oldSelectedPosition = selectedPosition;
             selectedPosition = holder.getAbsoluteAdapterPosition();
             notifyItemChanged(oldSelectedPosition);
             notifyItemChanged(selectedPosition);
             publisher.publish(item);
          }
      );
   }

   @Override
   public int getItemCount() {
      return list.size();
   }

   static class Holder extends RecyclerView.ViewHolder {
      private final DayStampItemBinding binding;

      public Holder(@NonNull DayStampItemBinding binding) {
         super(binding.getRoot());
         this.binding = binding;
      }
   }

   public interface Publisher {
      void publish(UiDayStamp uiDayStamp);
   }
}
