package com.anonyxhappie.dwarf.pms2.network;

/**
 * Created by dwarf on 9/28/2017.
 */

public interface AsyncTaskCompleteListener<T> {

    void onTaskComplete(T result);

}