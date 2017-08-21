package knd.com.lib_core.manager;

import com.google.firebase.database.FirebaseDatabase;

import io.realm.Realm;
import knd.com.lib_core.repository.BaseRepository;

/**
 * Created by itphe on 8/21/2017.
 */

public abstract class RepositoryManager {
    private BaseRepository mBaseRepository;

    public RepositoryManager(Realm c, FirebaseDatabase s) {
    }
}
