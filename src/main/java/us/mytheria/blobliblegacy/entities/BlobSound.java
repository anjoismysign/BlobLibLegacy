package us.mytheria.blobliblegacy.entities;

import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;

public class BlobSound {
    private final Sound sound;
    private final float volume;
    private final float pitch;
    private final SoundCategory soundCategory;

    public BlobSound(Sound sound, float volume, float pitch, @Nullable SoundCategory soundCategory) {
        this.sound = sound;
        this.volume = volume;
        this.pitch = pitch;
        this.soundCategory = soundCategory;
    }

    public void play(Player player) {
        if (soundCategory == null)
            player.playSound(player.getLocation(), sound, volume, pitch);
        else
            player.playSound(player.getLocation(), sound, soundCategory, volume, pitch);
    }

    public Sound sound(){
        return sound;
    }

    public float volume(){
        return volume;
    }

    public float pitch(){
        return pitch;
    }

    @Nullable
    public SoundCategory soundCategory(){
        return soundCategory;
    }
}
