package knd.com.lib_api.listener;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

/**
 * @Copy Right 2012-2016, Afinos, Inc., or its affiliates
 * @Author: Afinos Team
 * @Created by phearom on 9/9/16 3:56 PM
 **/
public abstract class SimpleValueEventListener implements ValueEventListener {
    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
