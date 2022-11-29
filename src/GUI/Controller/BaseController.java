package GUI.Controller;

import GUI.Model.MYTModel;

public abstract class BaseController {
    private MYTModel model;

    public void setModel(MYTModel model) {
        this.model = model;
    }

    public MYTModel getModel() {
        return model;
    }
    public abstract void setup();
}
