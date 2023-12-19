package a77777_888.me.t.weathertestapp.model.loadresults;

import a77777_888.me.t.weathertestapp.model.UiForecast;

@SuppressWarnings("unused")

public record LoadResult<T>(T value) {

   public Boolean isSuccess() {
      return value instanceof UiForecast;
   }

   public Boolean isError() {
      return value instanceof Exception;
   }

   public Boolean isLoading() {
      return value instanceof Boolean;
   }
}
