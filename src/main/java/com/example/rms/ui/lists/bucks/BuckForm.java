package com.example.rms.ui.lists.bucks;

import com.example.rms.entities.bucks.Buck;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

public class BuckForm extends FormLayout {
    Binder<Buck> binder = new BeanValidationBinder<>(Buck.class);

    DatePicker dOB = new DatePicker("Date of Birth");
    TextField cageRef = new TextField("Cage");
    TextField breed = new TextField("Breed");
    TextField weight = new TextField("Weight(Kg)");


    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button cancel = new Button("Cancel");
    private Buck buck;

    public BuckForm() {
        addClassName("buck-form");
        binder.bindInstanceFields(this);

        add(
                dOB,
                cageRef,
                breed,
                weight,
                createButtonsLayout()
        );
    }
    public void setBuck(Buck buck) {
        this.buck = buck;
        binder.readBean(buck);
    }
    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(
                new DeleteEvent(this, buck)
        ));
        cancel.addClickListener(event -> fireEvent(
                new CloseEvent(this)
        ));

        save.addClickShortcut(Key.ENTER);
        delete.addClickShortcut(Key.ESCAPE);
        return new HorizontalLayout(save, delete, cancel);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(buck);
            fireEvent(new SaveEvent(this, buck));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public static abstract class BuckFormEvent extends ComponentEvent<BuckForm> {
        private Buck buck;

        protected BuckFormEvent(BuckForm source, Buck buck) {
            super(source, false);
            this.buck = buck;
        }

        public Buck getBuck() {
            return buck;
        }
    }
    public static class SaveEvent extends BuckFormEvent {
        SaveEvent(BuckForm source, Buck buck) {
            super(source, buck);
        }
    }
    public static class DeleteEvent extends BuckFormEvent {
        DeleteEvent(BuckForm source, Buck buck) {
            super(source, buck);
        }
    }
    public static class CloseEvent extends BuckFormEvent {
        CloseEvent(BuckForm source) {
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
