package a77777_888.me.t.weathertestapp.model;

import androidx.annotation.DrawableRes;

public record DrawableResources(int background, int icon, int simpleIcon) {
    public DrawableResources(
        @DrawableRes int background, @DrawableRes int icon, @DrawableRes int simpleIcon
    ) {
        this.background = background;
        this.icon = icon;
        this.simpleIcon = simpleIcon;
    }
}
