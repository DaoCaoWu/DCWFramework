package cn.ninegame.library.component.mvc.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adao12 on 2015/10/17.
 */
public class Observable {

    List<Observer> mObservers = new ArrayList<Observer>();

    boolean changed = false;

    /**
     * Constructs a new {@code Observable} object.
     */
    public Observable() {
    }

    /**
     * Adds the specified observer to the list of observers. If it is already
     * registered, it is not added a second time.
     *
     * @param observer the Observer to add.
     */
    public void addObserver(Observer observer) {
        if (observer == null) {
            throw new NullPointerException("observer == null");
        }
        synchronized (this) {
            if (!mObservers.contains(observer))
                mObservers.add(observer);
        }
    }

    /**
     * Clears the changed flag for this {@code Observable}. After calling
     * {@code clearChanged()}, {@code hasChanged()} will return {@code false}.
     */
    protected void clearChanged() {
        changed = false;
    }

    /**
     * Returns the number of observers registered to this {@code Observable}.
     *
     * @return the number of observers.
     */
    public int countObservers() {
        return mObservers.size();
    }

    /**
     * Removes the specified observer from the list of observers. Passing null
     * won't do anything.
     *
     * @param observer the observer to remove.
     */
    public synchronized void deleteObserver(Observer observer) {
        mObservers.remove(observer);
    }

    /**
     * Removes all observers from the list of observers.
     */
    public synchronized void deleteObservers() {
        mObservers.clear();
    }

    /**
     * Returns the changed flag for this {@code Observable}.
     *
     * @return {@code true} when the changed flag for this {@code Observable} is
     * set, {@code false} otherwise.
     */
    public boolean hasChanged() {
        return changed;
    }

    /**
     * If {@code hasChanged()} returns {@code true}, calls the {@code update()}
     * method for every observer in the list of observers using null as the
     * argument. Afterwards, calls {@code clearChanged()}.
     * <p/>
     * Equivalent to calling {@code notifyObservers(null)}.
     */
    public void notifyObservers() {
        notifyObservers(null);
    }

    /**
     * If {@code hasChanged()} returns {@code true}, calls the {@code update()}
     * method for every Observer in the list of observers using the specified
     * argument. Afterwards calls {@code clearChanged()}.
     *
     * @param data the argument passed to {@code update()}.
     */
    @SuppressWarnings("unchecked")
    public <T> void notifyObservers(T data, Object... args) {
        int size = 0;
        Observer[] arrays = null;
        synchronized (this) {
            if (hasChanged()) {
                clearChanged();
                size = mObservers.size();
                arrays = new Observer[size];
                mObservers.toArray(arrays);
            }
        }
        if (arrays != null) {
            for (Observer observer : arrays) {
                observer.update(this, data, args);
            }
        }
    }

    /**
     * Sets the changed flag for this {@code Observable}. After calling
     * {@code setChanged()}, {@code hasChanged()} will return {@code true}.
     */
    protected void setChanged() {
        changed = true;
    }
}
