package cn.ninegame.library.component.adapter.model.select;

import java.util.List;

public interface ISelect<T> {

    /**
     * select mode
     * public static final int TYPE_SELECT_SINGLE = 1; // single
     * public static final int TYPE_SELECT_MULTI = 2;  // multi
     *
     * @param selectedMode
     */
    void setSelectedMode(int selectedMode);

    /**
     * get the delegate which will response the select event
     *
     * @return
     */
    OnItemSelectedListener getOnItemSelectedListener();

    /**
     * @param OnItemSelectedListener the delegate which will response the select event
     */
    void setOnItemSelectedListener(OnItemSelectedListener onSelectedListener);

    /**
     * restore the state into init. exclude select mode and data source.
     */
    void restore();

    /**
     * restore the state into init. exclude select mode and data source.
     * while set a new select limit num.
     *
     * @param maxSelectedNum
     */
    void restore(int maxSelectedNum);

    /**
     * judge whether the data of position is selected or not.
     *
     * @param pos
     * @return
     */
    boolean isSelected(int pos);

    /**
     * judge whether the data of position can be selected or not.
     *
     * @param pos
     * @return
     */
    boolean canBeSelected(int pos);

    /**
     * set the data of position to be selectable.
     *
     * @param pos
     * @param canBeSelected
     */
    void setCanBeSelected(int pos, boolean canBeSelected);

    /**
     * make the data of pos is isSelected
     *
     * @param pos
     * @param isSelected
     */
    void select(int pos, boolean isSelected);

    /**
     * get the num of selected
     *
     * @return
     */
    int getSelectedCount();

    /**
     * get index of last selected data, only working on TYPE_SELECT_SINGLE mode.
     *
     * @return
     */
    int getLastSelectedIndex();

    /**
     * just make index point to the data of position @index, only working on TYPE_SELECT_SINGLE mode,
     * and will not invoke any event.
     *
     * @param index
     */
    void setLastSelectedIndex(int index);

    /**
     * just make index point to the data that is marked with @id, only working on TYPE_SELECT_SINGLE mode,
     * and will not invoke any event.
     *
     * @param id
     */
    void setInitSelectedId(long id);

    /**
     * Is single mode?
     *
     * @return
     */
    boolean isSingleSelectedMode();

    /**
     * get the selected data object,only working on TYPE_SELECT_SINGLE mode
     *
     * @return
     */
    T getLastSelected();

    /**
     * get the selected data object list
     *
     * @return
     */
    List<T> getSelectedData();
}
