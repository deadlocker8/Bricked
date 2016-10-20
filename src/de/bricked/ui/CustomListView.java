package de.bricked.ui;

import java.util.Set;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollBar;
import javafx.scene.input.MouseEvent;


public class CustomListView<T> extends ListView<T>
{

    private EventHandler eventHandler = null;
    private ListChangeListener listChangeListener = null;

    public CustomListView(ObservableList<T> items)
    {
        super(items);
    }

    /**
     * Please copy this in the listView class. Thanks Oracle.
     */
    public void setHScrollBarEnabled(boolean value)
    {
        setScrollBarEnabled(value, Orientation.HORIZONTAL);
    }

    private void setScrollBarEnabled(boolean value, Orientation orientation)
    {
        Set<Node> set = this.lookupAll("VirtualScrollBar");

        for (Node n : set)
        {
            ScrollBar bar = (ScrollBar) n;
            if (bar.getOrientation() == orientation)
            {
                if (value)
                {
                    bar.setVisible(true);
                    bar.setDisable(false);
                    bar.setStyle("-fx-opacity: 100%");
                }
                else
                {
                    bar.setVisible(false);
                    bar.setDisable(true);
                    bar.setStyle("-fx-opacity: 0%");
                }
            }
        }
    }

    public void setVScrollBarEnabled(boolean value)
    {
        setScrollBarEnabled(value, Orientation.VERTICAL);
    }

    /**
     * It's really not hard to build it into your listView oracle.
     */
    public void setSelectable(boolean value)
    {

        if (value)
        {
            if (eventHandler != null)
            {
                removeEventFilter(MouseEvent.ANY, eventHandler);
                eventHandler = null;
            }
        }
        else
        {
            eventHandler = new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent event)
                {
                    event.consume();
                }

            };
            addEventFilter(MouseEvent.ANY, eventHandler);
        }
    }

    public void setAutoScrollEnabled(boolean value)
    {
        if (value)
        {
            listChangeListener = new ListChangeListener()
            {
                @Override
                public void onChanged(Change c)
                {
                    c.next();
                    final int size = getItems().size();
                    if (size > 0)
                    {
                        scrollTo(size - 1);
                    }
                }
            };
            getItems().addListener(listChangeListener);
        }
        else
        {
            if (listChangeListener != null)
            {
                getItems().removeListener(listChangeListener);
                listChangeListener = null;
            }
        }
    }
}