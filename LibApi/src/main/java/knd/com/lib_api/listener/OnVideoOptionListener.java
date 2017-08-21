package knd.com.lib_api.listener;

import com.afinos.studyspaced.ui.fragment.media.tools.MediaObject;

/**
 * @Copy Right 2012-2016, Afinos, Inc., or its affiliates
 * @Author: Afinos Team
 **/
public interface OnVideoOptionListener {
    void addToPlaylist(MediaObject mediaObject);

    void addToFavorite(MediaObject mediaObject);
}
