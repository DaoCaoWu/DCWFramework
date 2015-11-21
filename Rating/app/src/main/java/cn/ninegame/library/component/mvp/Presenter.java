package cn.ninegame.library.component.mvp;

/**
 * Created by jiaying.cjy@alibaba-inc.com on 2015/11/21.
 */
/**
 * Abstract presenter to work as base for every presenter created in the application. This
 * presenter
 * declares some methods to attach the fragment/activity lifecycle.
 *
 * @author Pedro Vicente Gómez Sánchez
 */
public abstract class Presenter {

    /**
     * Called when the presenter is initialized, this method represents the start of the presenter
     * lifecycle.
     */
    public abstract void initialize();

    /**
     * Called when the presenter is resumed. After the initialization and when the presenter comes
     * from a pause state.
     */
    public abstract void resume();

    /**
     * Called when the presenter is paused.
     */
    public abstract void pause();
}
