package a77777_888.me.t.weathertestapp.ui.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import a77777_888.me.t.weathertestapp.databinding.TimestampLayoutBinding;
import a77777_888.me.t.weathertestapp.model.UiTimestamp;

public class TimestampAdapter extends RecyclerView .Adapter<TimestampAdapter.Holder>{

   @NonNull
   private ArrayList<UiTimestamp> list;

   public TimestampAdapter(@NonNull ArrayList<UiTimestamp> list) {
      this.list = list;
   }

   @SuppressLint("NotifyDataSetChanged")
   public void setList(@NonNull ArrayList<UiTimestamp> list) {
      this.list = list;
      notifyDataSetChanged();
   }

   @NonNull
   @Override
   public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
      TimestampLayoutBinding binding = TimestampLayoutBinding.inflate(
          layoutInflater, parent, false
      );
      return new Holder(binding);
   }

   @Override
   public void onBindViewHolder(@NonNull Holder holder, int position) {
      UiTimestamp item = list.get(position);
      holder.binding.timeTextView.setText(item.getTimeString());
      holder.binding.weatherIconImageView.setImageResource(item.getDrawableResources().simpleIcon());
      holder.binding.temperatureTextView.setText(item.getTemperature());
   }

   @Override
   public int getItemCount() {
      return list.size();
   }

   public static class Holder extends RecyclerView.ViewHolder {
      private final TimestampLayoutBinding binding;

      public Holder(@NonNull TimestampLayoutBinding binding) {
         super(binding.getRoot());
         this.binding = binding;
      }
   }
}
