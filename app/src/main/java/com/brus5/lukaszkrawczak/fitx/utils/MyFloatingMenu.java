package com.brus5.lukaszkrawczak.fitx.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;

import com.brus5.lukaszkrawczak.fitx.MainActivity;
import com.brus5.lukaszkrawczak.fitx.R;
import com.brus5.lukaszkrawczak.fitx.diet.DietActivity;
import com.brus5.lukaszkrawczak.fitx.settings.list.SettingsActivity;
import com.brus5.lukaszkrawczak.fitx.stats.StatsActivity;
import com.brus5.lukaszkrawczak.fitx.training.TrainingActivity;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.logging.Handler;

public class MyFloatingMenu
{
    private static final String TAG = "MyFloatingMenu";
    private Context context;
    private FloatingActionMenu fem;

    public MyFloatingMenu(final Context context)
    {
        this.context = context;

        fem = ((Activity) context).findViewById(R.id.menu);

        animFadeIn();

        fem.addMenuButton(button("Dieta", R.drawable.ic_local_dining_white_24dp, DietActivity.class));
        fem.addMenuButton(button("Trening", R.drawable.ic_fitness_center_white_24dp, TrainingActivity.class));
        fem.addMenuButton(button("Ustawienia", R.drawable.ic_settings_white_24dp, SettingsActivity.class));
        fem.addMenuButton(button("Statystyki", R.drawable.ic_timeline_white_24dp, StatsActivity.class));
        fem.setAnimated(true);

        fem.setOnMenuButtonLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view)
            {
                Log.d(TAG, "onLongClick() called with: view = [" + view + "]");
//                fem.setPadding(0,0,8,8);

//                ConstraintLayout cl = ((Activity) context).findViewById(R.id.constraintLayout);
//                ConstraintSet cs = new ConstraintSet();
//                cs.clone(cl);
//                cs.connect(R.id.menu,ConstraintSet.RIGHT,R.id.menu, ConstraintSet.LEFT,0);
//                cs.applyTo(cl);

                animFadeOut();
                animFadeIn();

                return false;
            }
        });
/*To set constraints of image view to:

app:layout_constraintRight_toRightOf="@+id/check_answer2"
app:layout_constraintTop_toTopOf="@+id/check_answer2"
use:

ConstraintSet constraintSet = new ConstraintSet();
constraintSet.clone(constraintLayout);
constraintSet.connect(R.id.imageView,ConstraintSet.RIGHT,R.id.check_answer2,ConstraintSet.RIGHT,0);
constraintSet.connect(R.id.imageView,ConstraintSet.TOP,R.id.check_answer2,ConstraintSet.TOP,0);
constraintSet.applyTo(constraintLayout);
*/
    }

    private FloatingActionButton button(String name, int resID, final Class<?> classDestination)
    {
        FloatingActionButton b = new FloatingActionButton(context);
        b.setLabelText(name);
        b.setImageResource(resID);

        b.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                fem.close(true);

                animFadeOut();

                new Thread(
                    new Runnable() {
                        @Override
                        public void run()
                        {
                            try
                            {
                                Thread.sleep(500);

                                if (context.getClass().getSimpleName().equals(MainActivity.class.toString()))
                                    runNextActivity(classDestination);

                                if (!context.getClass().getSimpleName().equals(MainActivity.class.toString()))
                                {
                                    runNextActivity(classDestination);
                                    ((Activity)context).finish();
                                    Log.d(TAG, "onClick() called with: v = [" + context.getClass().getSimpleName() + "]");
                                }

                            }
                            catch (InterruptedException e)
                            {
                                e.printStackTrace();
                            }
                        }
                    }
                ).start();
            }
        });
        return b;
    }

    /**
     * We can set parameters of animation in res.anim.fadeout.xml
     */
    private void animFadeOut()
    {
        fem.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fadeout));
        fem.setVisibility(View.INVISIBLE);
    }

    /**
     * We can set parameters of animation in res.anim.fadein.xml
     */
    private void animFadeIn()
    {
        fem.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fadein));
        fem.setVisibility(View.VISIBLE);
    }

    public void close() throws NoSuchMethodError
    {
        fem.close(true);
    }

    private void runNextActivity(Class<?> cls)
    {
        Intent intent = new Intent(this.context, cls);
        this.context.startActivity(intent);
    }
}
