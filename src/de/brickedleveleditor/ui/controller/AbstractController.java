package de.brickedleveleditor.ui.controller;

import javafx.stage.Stage;


public abstract class AbstractController
{
    protected AbstractController parentController;
    protected Stage stage;

    public void init(Stage stage, AbstractController parentController)
    {
        this.parentController = parentController;
        this.stage = stage;
        initController();
    }

    protected abstract void initController();

}
