/*
 * Copyright (c) 2009-2010, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice,
 *   this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * * Neither the name of Oracle nor the names of its contributors
 *   may be used to endorse or promote products derived from this software without
 *   specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

//
// Implementation of truck class for "Freeway".
//

#include <math.h>
#include <gtk/gtk.h>
#include "vehicle.h"
#include "truck.h"


const double DELTA_T = 0.0000555; // 1/5 sec expressed in hours
const double OPT_DT = 0.001; // optimal buffer (in hrs) in front of me
const double CAR_LEN = 0.0091; // truck length (in mi) (roughly 48 feet)
const double BRAKE_DV = 2.0; // 10 mph / sec for 1/5 sec
const int CAR_LENGTH = 20;
const int CAB_LENGTH = 3;
const int CAR_WIDTH = 5;

Truck::Truck(int i, int l, double p, double v) {
    classID = CLASS_TRUCK;
    name_int = i;
    lane_num = l;
    position = p;
    velocity = v;
    state = VSTATE_MAINTAIN;
    max_speed = 70;
    xlocation = 0;
    ylocation = 0;
    change_state = 0;
    restrict_change = 0;
    absent_mindedness = 0;
}

double
Truck::vehicle_length() {
    return CAR_LEN;
}

void
Truck::draw(GdkDrawable *pix, GdkGC *gc, int x, int y, 
        int direction_right, int scale, int xorg, int yorg, int selected) {
    extern GdkColor *color_black;

    this->xloc(x);
    this->yloc(y);

    // If I am heading to the right, then I need to draw brick to the left of 
    // front of car.  If I am heading left, draw brick to the right.
    if (direction_right) {
        x -= (CAR_LENGTH - 1);
    }

    int l = x * scale + xorg;
    int t = y * scale + yorg;
    int w = CAR_LENGTH * scale;
    int h = CAR_WIDTH * scale;

    // Draw brick.
    gdk_draw_rectangle(pix, gc, TRUE, l, t, w, h);

    gdk_gc_set_foreground(gc, color_black);

    int x1 = l;
    int y1 = t;
    int notch_height = scale * ((CAR_WIDTH - 1) / 2);

    // Put notches on left or right, depending on vehicle direction
    if (direction_right) {
        x1 += scale * (CAR_LENGTH - CAB_LENGTH - 1);
    } else {
        x1 += scale * CAB_LENGTH;
    }

    // Put notches on the top and bottom, separating cab from trailer
    gdk_draw_rectangle(pix, gc, TRUE, x1, y1, scale, notch_height);
    y1 += scale * (CAR_WIDTH - 2);
    gdk_draw_rectangle(pix, gc, TRUE, x1, y1, scale, notch_height);

    // Put red box around "current vehicle"
    if (selected) {
        draw_selection(pix, gc, l, t, w, h, scale);
    }
}

double
Truck::optimal_dist(Vehicle *in_front) {
    // Calculate optimal following distance based on my velocity and the 
    // difference in velocity from the car in front.
    double dv = in_front->vel() - velocity;

    return (OPT_DT * velocity + (0.5 * dv * dv * DELTA_T / BRAKE_DV));
}

void
Truck::recalc_velocity() {
    // Update velocity based on state
    switch (state) {
        case VSTATE_COAST: velocity *= 0.98;
            break;
        case VSTATE_BRAKE: velocity -= BRAKE_DV;
            break;
        case VSTATE_ACCELERATE: velocity += 0.25;
            break;
        case VSTATE_MAINTAIN: break;
        case VSTATE_CRASH: velocity = 0.00;
            break;
        case VSTATE_MAX_SPEED: velocity = max_speed;
            break;
        case VSTATE_CHANGE_LANE: break;
        case VSTATE_CHANGE_LEFT: break;
        case VSTATE_CHANGE_RIGHT: break;
    }
    if (velocity < 0.0) {
        velocity = 0.0;
    }
}
