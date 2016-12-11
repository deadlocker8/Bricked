package de.bricked.game.sound;

public enum SoundType
{
	HIT_WALL("hit_wall"),
	HIT_PADDLE("hit_paddle"),
	HIT_BRICK("hit_brick"),
	DESTROY_BRICK("destroy_brick"),
	TNT("tnt"),
	LIFE_LOST("life_lost"),
	GAME_OVER("game_over"),
	FINISHED_LEVEL("finished_level"),
	UNLOCKED("unlocked");
	
	private String fileName;
	
	private SoundType(String fileName)
	{
		this.fileName = fileName;
	}

	public String getFileName()
	{
		return fileName;
	}
}