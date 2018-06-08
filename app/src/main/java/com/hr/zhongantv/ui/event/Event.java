package com.hr.zhongantv.ui.event;

/**
 * Created by 吕 on 2018/6/8.
 */

public class Event {
    public static class UpFocusEvent{
        private int type;

        public UpFocusEvent(int type) {
            this.type = type;
        }

        public int getType() {
            return type;
        }
    }
}
