package com.example.rms.ui.lists.weaners;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import com.example.rms.entities.weaner.Weaner;

public class WeanerForm extends VerticalLayout {
    Binder<Weaner> binder = new BeanValidationBinder<>(Weaner.class);

    DatePicker dOB = new DatePicker("Date of Birth");
    TextField cageRef = new TextField("Cage");
    TextField parentDoe = new TextField("Parent Doe");
    TextField sire = new TextField("Sire");
    IntegerField initialCount = new IntegerField("Initial Count");
    IntegerField currentCount = new IntegerField("Current Count");
    
    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button cancel = new Button("Cancel");
    private Weaner weaner;

    public WeanerForm() {
        addClassName("weaner-form");
        binder.bindInstanceFields(this);

        add(
                dOB,
                cageRef,
                parentDoe,
                sire,
                counts(),
                createButtonsLayout()
        );
    }

    private Component counts() {
        initialCount.setHasControls(true);
        currentCount.setHasControls(true);

        return new VerticalLayout(initialCount,currentCount);
    }

    public void setWeaner(Weaner weaner) {
        this.weaner = weaner;
        binder.readBean(weaner);
    }
    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(
                new WeanerForm.DeleteEvent(this, weaner)
        ));
        cancel.addClickListener(event -> fireEvent(
                new WeanerForm.CloseEvent(this)
        ));

        save.addClickShortcut(Key.ENTER);
        delete.addClickShortcut(Key.ESCAPE);
        return new HorizontalLayout(save, delete, cancel);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(weaner);
            fireEvent(new WeanerForm.SaveEvent(this, weaner));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public static abstract class WeanerFormEvent extends ComponentEvent<WeanerForm> {
        private Weaner weaner;

        protected WeanerFormEvent(WeanerForm source, Weaner weaner) {
            super(source, false);
            this.weaner = weaner;
        }

        public Weaner getWeaner() {
            return weaner;
        }
    }
    public static class SaveEvent extends WeanerForm.WeanerFormEvent {
        SaveEvent(WeanerForm source, Weaner weaner) {
            super(source, weaner);
        }
    }
    public static class DeleteEvent extends WeanerForm.WeanerFormEvent {
        DeleteEvent(WeanerForm source, Weaner weaner) {
            super(source, weaner);
        }
    }
    public static class CloseEvent extends WeanerForm.WeanerFormEvent {
        CloseEvent(WeanerForm source) {
            super(source, null);
        }
    }
    public <T extends ComponentEvent<?>> Registration addListener(Class<T>
                                                                          eventType,
                                                                  ComponentEventListener<T>
                                                                          listener) {

        return getEventBus().addListener(
                eventType, listener);
    }
}
