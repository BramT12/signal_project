package com.alerts.Week7.Decorator;

    public class PriorityAlertDecorator extends AlertDecorator {
        private int priority;
    
        public PriorityAlertDecorator(Alert decoratedAlert, int priority) {
            super(decoratedAlert);
            this.priority = priority;
        }
    
        @Override
        public void triggerAlert() {
            super.triggerAlert();
            System.out.println("Alert Priority: " + priority);
        }
}