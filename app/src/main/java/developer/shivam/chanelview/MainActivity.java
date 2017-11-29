package developer.shivam.chanelview;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import developer.shivam.chanelview.util.HelperView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, GestureDetector.OnGestureListener {

    Context mContext = MainActivity.this;

    private LinearLayout tilesContainer;
    private ScrollView mainScrollView;

    private int[] colors = new int[5];

    private int ANIMATION_DURATION = 300; //in milliseconds
    private int firstChildHeight;
    private int defaultChildHeight;

    private boolean toAnimate = true;
    private boolean toFantasticScroll = true;

    private GestureDetectorCompat detector;
    private Toolbar appBar;

    NavigationView navigationView;

    private String[] messages = {
            "Categories",
            "Top Downloads",
            "Top Viewed",
            "Send Feedback",
            "Contact Us"
    };

    private String[] tagLines = {
            "Maybe the bike is more dangerous, but the passion for the car for me is second to the bike. \n - Valentino Rossi",
            "Better to live one year as a tiger, than a hundred as a sheep. \n - Madonna",
            "Always focus on the front windshield and not the review mirror. \n - Colin Powell",
            "", ""
    };

    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mDrawerToggle;
    private SampleFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appBar = (Toolbar) findViewById(R.id.landingPageAppBar);
        setSupportActionBar(appBar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
        }

        setUpNavigationDrawer();

        detector = new GestureDetectorCompat(this, this);
        int displayHeight = getWindowManager().getDefaultDisplay().getHeight();
        firstChildHeight = (displayHeight * 60) / 100; //first tile should cover 60% of height
        defaultChildHeight = displayHeight / 6;

        tilesContainer = (LinearLayout) findViewById(R.id.tileContainer);

        mainScrollView = (ScrollView) findViewById(R.id.mainScrollView);
        mainScrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        addTilesToContainer();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (toFantasticScroll) {
            detector.onTouchEvent(ev);
        }
        return super.dispatchTouchEvent(ev);
    }

    public void addTilesToContainer() {

        View tileView;

        int[] images = {
                R.drawable.image_one,
                R.drawable.image_two,
                R.drawable.image_three,
                R.drawable.image_four,
                R.drawable.image_five
        };

        int numberOfTiles = 5;
        for (int i = 0; i < numberOfTiles; i++) {
            if (i == 0) {
                tileView = LayoutInflater.from(mContext).inflate(R.layout.chanel_layout, null);
                tileView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        firstChildHeight));
                tileView.setTag("tile - " + messages[i]);
                tileView.setOnClickListener(this);

                ImageView imageView = (ImageView) tileView.findViewById(R.id.ivBackground);
                imageView.setImageResource(images[i]);
                tileView.setOnClickListener(this);

                Button button = (Button) tileView.findViewById(R.id.btnTileMessage);
                button.setText(messages[i]);
                button.setTag(i);
                button.setOnClickListener(this);

                TextView tagLine = (TextView) tileView.findViewById(R.id.tvTileTagLine);
                tagLine.setText(tagLines[i]);

                tilesContainer.addView(tileView);
            } else {
                tileView = LayoutInflater.from(mContext).inflate(R.layout.chanel_layout, null);
                tileView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        defaultChildHeight));
                tileView.setTag("tile - " + messages[i]);
                tileView.setOnClickListener(this);

                ImageView imageView = (ImageView) tileView.findViewById(R.id.ivBackground);
                imageView.setImageResource(images[i]);
                tileView.setOnClickListener(this);

                Button button = (Button) tileView.findViewById(R.id.btnTileMessage);
                button.setText(messages[i]);
                button.setTag(i);
                button.setOnClickListener(this);

                TextView tagLine = (TextView) tileView.findViewById(R.id.tvTileTagLine);
                tagLine.setText(tagLines[i]);
                tagLine.setVisibility(View.INVISIBLE);

                tilesContainer.addView(tileView);
            }
        }

        HelperView.setPrecedingView(null);
        HelperView.setCurrentView(tilesContainer.getChildAt(0));
        HelperView.getCurrentView().findViewById(R.id.tvTileTagLine).setVisibility(View.VISIBLE);
        HelperView.setFollowingView(tilesContainer.getChildAt(1));

    }

    private void addFragmentToScreen(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().add(R.id.frame_layout, fragment, "sample_fragment").commit();
    }

    @Override
    public void onClick(final View v) {

        if (v.getTag().toString().contains("tile")) {
            if (v.getLayoutParams().height != firstChildHeight) {
                expandView(v);
            } else {
                switch (tilesContainer.indexOfChild(v)) {
                    case 0:
                        if (tilesContainer.getChildAt(0).getLayoutParams().height != firstChildHeight) {
                            downToUpScroll(HelperView.getCurrentView(), HelperView.getFollowingView());
                        } else {
                            isFragmentOpened = true;
                            fragment = (SampleFragment) Fragment.instantiate(this, SampleFragment.class.getName());
                            addFragmentToScreen(fragment);
                        }
                        break;

                    case 1:
                        if (tilesContainer.getChildAt(1).getLayoutParams().height != firstChildHeight) {
                            downToUpScroll(HelperView.getCurrentView(), HelperView.getFollowingView());
                        }
                        break;

                    case 2:
                        if (tilesContainer.getChildAt(2).getLayoutParams().height != firstChildHeight) {
                            downToUpScroll(HelperView.getCurrentView(), HelperView.getFollowingView());
                        }
                        break;

                    case 3:
                        break;

                    case 4:
                        break;
                }
            }
        }

        switch (v.getTag().toString()) {
            case "0":
                Toast.makeText(this, "Open Categories", Toast.LENGTH_SHORT).show();
                break;

            case "1":
                if (tilesContainer.getChildAt(1).getLayoutParams().height != firstChildHeight) {
                    downToUpScroll(HelperView.getCurrentView(), HelperView.getFollowingView());
                }
                break;

            case "2":
                if (tilesContainer.getChildAt(2).getLayoutParams().height != firstChildHeight) {
                    downToUpScroll(HelperView.getCurrentView(), HelperView.getFollowingView());
                }
                break;

            case "3":
                break;

            case "4":
                break;
        }
    }

    public void expandView(final View view) {
        int currentScrollPosition = mainScrollView.getScrollY();
        int finalScrollPosition = view.getTop();

        ValueAnimator scrollAnimator = ValueAnimator.ofInt(currentScrollPosition, finalScrollPosition);
        scrollAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int amount = (int) animation.getAnimatedValue();
                mainScrollView.scrollTo(0, amount);
            }
        });
        scrollAnimator.setDuration(ANIMATION_DURATION);

        ValueAnimator heightAnimator = ValueAnimator.ofInt(view.getHeight(), firstChildHeight);
        heightAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int height = (int) animation.getAnimatedValue();
                view.getLayoutParams().height = height;
                view.requestLayout();
            }
        });
        heightAnimator.setDuration(ANIMATION_DURATION);

        scrollAnimator.start();
        heightAnimator.start();

        if (tilesContainer.indexOfChild(view) == 0) {
            //do nothing
        } else {
            HelperView.setPrecedingView(tilesContainer.getChildAt(tilesContainer.indexOfChild(view) - 1));
        }
        HelperView.setCurrentView(view);
        HelperView.setFollowingView(tilesContainer.getChildAt(tilesContainer.indexOfChild(view) + 1));
        HelperView.getCurrentView().findViewById(R.id.tvTileTagLine).setVisibility(View.VISIBLE);
        if (tilesContainer.indexOfChild(view) < 4) {
            HelperView.getFollowingView().findViewById(R.id.tvTileTagLine).setVisibility(View.INVISIBLE);
        }
    }


    @Override
    public boolean onDown(MotionEvent e) {
        //return true because every gesture start with onDown
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        final int SWIPE_MIN_DISTANCE = 50;
        final int SWIPE_THRESHOLD_VELOCITY = 200;

        if (e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE &&
                Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
            //Toast.makeText(this, "Bottom to top", Toast.LENGTH_SHORT).show();
            if (HelperView.getFollowingView() != null) {
                downToUpScroll(HelperView.getCurrentView(), HelperView.getFollowingView());
            }
            //From Bottom to top
            return true;
        } else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE &&
                Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
            //Toast.makeText(this, "top to Bottom", Toast.LENGTH_SHORT).show();
            if (HelperView.getPrecedingView() != null) {
                upToDownScroll(HelperView.getPrecedingView(), HelperView.getCurrentView());
            }
            //From top to Bottom
            return true;
        }

        return true;
    }

    public void upToDownScroll(final View precedingView, final View currentView) {

        if (toAnimate) {

            toAnimate = false;

            if (tilesContainer.indexOfChild(currentView) == 0) {
                //do-nothing
            } else {
                int currentScrollPosition = mainScrollView.getScrollY();
                int toScrollPosition = precedingView.getTop();

                ValueAnimator scrollAnimator = ValueAnimator.ofInt(currentScrollPosition, toScrollPosition);
                scrollAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int amount = (int) animation.getAnimatedValue();
                        mainScrollView.scrollTo(0, amount);
                    }
                });
                scrollAnimator.setDuration(ANIMATION_DURATION);

                ValueAnimator heightAnimator = ValueAnimator.ofInt(currentView.getLayoutParams().height, defaultChildHeight);
                heightAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int height = (int) animation.getAnimatedValue();
                        currentView.getLayoutParams().height = height;
                        currentView.requestLayout();
                    }
                });
                heightAnimator.setDuration(ANIMATION_DURATION);

                ValueAnimator precedingViewHeightAnimator = ValueAnimator.ofInt(precedingView.getLayoutParams().height, firstChildHeight);
                precedingViewHeightAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int height = (int) animation.getAnimatedValue();
                        precedingView.getLayoutParams().height = height;
                        precedingView.requestLayout();
                    }
                });
                precedingViewHeightAnimator.setDuration(ANIMATION_DURATION);

                scrollAnimator.start();
                heightAnimator.start();
                precedingViewHeightAnimator.start();

                HelperView.setCurrentView(precedingView);
                HelperView.setPrecedingView(tilesContainer.getChildAt(tilesContainer.indexOfChild(precedingView) - 1));
                HelperView.setFollowingView(currentView);
                HelperView.getCurrentView().findViewById(R.id.tvTileTagLine).setVisibility(View.VISIBLE);
                HelperView.getFollowingView().findViewById(R.id.tvTileTagLine).setVisibility(View.INVISIBLE);

                scrollAnimator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                        toAnimate = false;
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        toAnimate = true;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
            }
        }
    }

    public void downToUpScroll(View currentView, final View followingView) {

        if (toAnimate) {

            toAnimate = false;

            int currentScrollPosition = mainScrollView.getScrollY();
            int toScrollPosition = followingView.getTop();

            ValueAnimator scrollAnimator = ValueAnimator.ofInt(currentScrollPosition, toScrollPosition);
            scrollAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int amount = (int) animation.getAnimatedValue();
                    mainScrollView.scrollTo(0, amount);
                }
            });
            scrollAnimator.setDuration(ANIMATION_DURATION);

            ValueAnimator heightAnimator = ValueAnimator.ofInt(followingView.getHeight(), firstChildHeight);
            heightAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int height = (int) animation.getAnimatedValue();
                    followingView.getLayoutParams().height = height;
                    followingView.requestLayout();
                }
            });
            heightAnimator.setDuration(ANIMATION_DURATION);

            scrollAnimator.start();
            heightAnimator.start();

            scrollAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    toAnimate = true;
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });

            HelperView.setPrecedingView(currentView);
            HelperView.setCurrentView(followingView);
            HelperView.setFollowingView(tilesContainer.getChildAt(tilesContainer.indexOfChild(followingView) + 1));
            HelperView.getCurrentView().findViewById(R.id.tvTileTagLine).setVisibility(View.VISIBLE);
        }
    }

    public void setUpNavigationDrawer() {

        navigationView = (NavigationView) findViewById(R.id.fragmentDrawer);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.mainDrawerLayout);
        mDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout,
                appBar,
                R.string.open,
                R.string.close) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

                toFantasticScroll = true;
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                toFantasticScroll = false;
            }
        };

        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
    }

    boolean isFragmentOpened = false;

    @Override
    public void onBackPressed() {
        if (isFragmentOpened) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            isFragmentOpened = false;
        } else {
            super.onBackPressed();
        }
    }
}
