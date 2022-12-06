package GUI.Controller;

import GUI.Model.MYTModel;

public abstract class BaseController {
    public BaseController() {
        try {
            this.model = new MYTModel();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private MYTModel model;

    public void setModel(MYTModel model) {
        this.model = model;
    }

    public MYTModel getModel() {
        return model;
    }
    public abstract void setup();
}
