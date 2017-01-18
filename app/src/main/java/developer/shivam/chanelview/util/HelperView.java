package developer.shivam.chanelview.util;

import android.view.View;

public class HelperView {

    //The view above the currentView will be referred as precedingView
    public static View precedingView;

    //The main view is referenced as currentView
    public static View currentView;

    //The view below the currentView will be referred as followingView
    public static View followingView;

    public static View getPrecedingView() {
        return precedingView;
    }

    public static void setPrecedingView(View precedingView) {
        HelperView.precedingView = precedingView;
    }

    public static View getCurrentView() {
        return currentView;
    }

    public static void setCurrentView(View currentView) {
        HelperView.currentView = currentView;
    }

    public static View getFollowingView() {
        return followingView;
    }

    public static void setFollowingView(View followingView) {
        HelperView.followingView = followingView;
    }
}
