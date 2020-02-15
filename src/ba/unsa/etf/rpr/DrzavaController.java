package ba.unsa.etf.rpr;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.util.ArrayList;

public class DrzavaController {
    public TextField fieldNaziv;
    public ChoiceBox<Grad> choiceGrad;
    public  RadioButton radioIsti;
    public  RadioButton radioDrugi;
    public ChoiceBox<Grad> choiceGradNajveci;

    private ToggleGroup group;

    private Drzava drzava;
    private ObservableList<Grad> listGradovi;

    public DrzavaController(Drzava drzava, ArrayList<Grad> gradovi) {
        this.group = new ToggleGroup();
        this.drzava = drzava;
        listGradovi = FXCollections.observableArrayList(gradovi);
    }

    @FXML
    public void initialize() {
        choiceGrad.setItems(listGradovi);
        choiceGradNajveci.setItems(listGradovi);

        radioIsti.setToggleGroup(group);
        radioDrugi.setToggleGroup(group);

        if (drzava != null) {
            fieldNaziv.setText(drzava.getNaziv());
            Grad glavni = drzava.getGlavniGrad();
            for (Grad grad : listGradovi) {
                if (grad.getId() == glavni.getId()) {
                    choiceGrad.getSelectionModel().select(grad);
                }
            }
            radioDrugi.fire();
            Grad veci = drzava.getNajveciGrad();
            for (Grad grad : listGradovi) {
                if (grad.getId() == veci.getId()) {
                    choiceGradNajveci.getSelectionModel().select(grad);
                }
            }

        } else {
            choiceGrad.getSelectionModel().selectFirst();
            choiceGradNajveci.getSelectionModel().selectFirst();
            radioIsti.setSelected(true);
            choiceGradNajveci.setDisable(true);
        }





        /*fldIme.textProperty().addListener((obs, oldIme, newIme) -> {
            if (!newIme.isEmpty()) {
                fldIme.getStyleClass().removeAll("poljeNijeIspravno");
                fldIme.getStyleClass().add("poljeIspravno");
            } else {
                fldIme.getStyleClass().removeAll("poljeIspravno");
                fldIme.getStyleClass().add("poljeNijeIspravno");
            }
        });*/

        radioIsti.selectedProperty().addListener((obs, oldIme, newIme) -> {
            if(newIme){
                choiceGradNajveci.setDisable(true);
            }else {
                choiceGradNajveci.setDisable(false);
            }
        });

        choiceGrad.getSelectionModel().selectedIndexProperty().addListener((obs, oldIme, newIme) -> {
            choiceGradNajveci.getSelectionModel().select((Integer) newIme);
        });


    }

    public Drzava getDrzava() {
        return drzava;
    }

    public void clickOk(ActionEvent actionEvent) {
        boolean sveOk = true;

        if (fieldNaziv.getText().trim().isEmpty()) {
            fieldNaziv.getStyleClass().removeAll("poljeIspravno");
            fieldNaziv.getStyleClass().add("poljeNijeIspravno");
            sveOk = false;
        } else {
            fieldNaziv.getStyleClass().removeAll("poljeNijeIspravno");
            fieldNaziv.getStyleClass().add("poljeIspravno");
        }

        if (!sveOk) return;

        if (drzava == null) drzava = new Drzava();
        drzava.setNaziv(fieldNaziv.getText());
        drzava.setGlavniGrad(choiceGrad.getSelectionModel().getSelectedItem());
        drzava.setNajveciGrad(choiceGradNajveci.getSelectionModel().getSelectedItem());
        closeWindow();
    }

    public void clickCancel(ActionEvent actionEvent) {
        drzava = null;
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) fieldNaziv.getScene().getWindow();
        stage.close();
    }
}
