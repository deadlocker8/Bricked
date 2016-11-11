package de.bricked.game;

import tools.PathUtils;

public class Config
{
    public final static String FILESYSTEM_ROOT_DIR = PathUtils.getOSindependentPath()+"deadspaghetti/bricked/";
    public final static String FILESYSTEM_LEVELPACK_SAVEDIR = FILESYSTEM_ROOT_DIR+"levelpacks/";

    public final static String JAR_ROOT_DIR = "/de/bricked/resources/";
    public final static String JAR_LEVELPACK_SAVEDIR = JAR_ROOT_DIR+"levelpacks/";
    public final static String JAR_SOUND_SAVEDIR = JAR_ROOT_DIR + "sounds/";
}