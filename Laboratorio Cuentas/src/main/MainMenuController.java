/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import Domain.Account;
import Domain.Client;
import Domain.SavingsAccount;
import Domain.TermAccount;
import Domain.accountMaintenance;
import Domain.clientMaintenance;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableListValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author LuisGa
 */
public class MainMenuController implements Initializable {

    @FXML
    private TextField txfClientId;
    @FXML
    private Button btnDisplayClientData;
    @FXML
    private Button btnAddAccount;
    @FXML
    private Button btnAccounts;
    @FXML
    private Button btnClients;
    @FXML
    private Text txt1;
    @FXML
    private Text txt2;
    @FXML
    private Text txt3;
    @FXML
    private Text txt4;
    @FXML
    private Text txt5;
    @FXML
    private Text txt6;
    @FXML
    private Text txt7;
    @FXML
    private TextField txf1;
    @FXML
    private TextField txf2;
    @FXML
    private TextField txf3;
    @FXML
    private TextField txf4;
    @FXML
    private TextField txf5;
    @FXML
    private TextField txf6;
    @FXML
    private TextField txf7;
    @FXML
    private Text txtTitle;
    @FXML
    private Button btnAddClient;
    
    clientMaintenance cm;
    accountMaintenance am;
    @FXML
    private Text txtClientData;
    @FXML
    private Button btnSearch;
    @FXML
    private Text txtClientAccounts;
    @FXML
    private ListView<Account> listAccounts;
    @FXML
    private RadioButton rdbSavings;
    @FXML
    private RadioButton rdbTerm;
    @FXML
    private Button btnSaveAccount;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        clean();
        cm = new clientMaintenance();
        am = new accountMaintenance();
    }    

    @FXML
    private void btnDisplayClientData(ActionEvent event) {
    }

    @FXML
    private void btnAddAccount(ActionEvent event) {
        
        rdbSavings.setVisible(true);
        rdbTerm.setVisible(true);
        
    }

    @FXML
    private void btnAccounts(ActionEvent event) {
    }

    @FXML
    private void btnClients(ActionEvent event) {
        txt1.setText("Cédula");
        txf1.setVisible(true);
        txt2.setText("Nombre");
        txf2.setVisible(true);
        txt3.setText("Teléfono");
        txf3.setVisible(true);
        txt4.setText("Dirección");
        txf4.setVisible(true);
    }
    
    public void clean(){
    
        txt1.setText("");
        txf1.setVisible(false);
        txt2.setText("");
        txf2.setVisible(false);
        txt3.setText("");
        txf3.setVisible(false);
        txt4.setText("");
        txf4.setVisible(false);
        txt5.setText("");
        txf5.setVisible(false);
        txt6.setText("");
        txf6.setVisible(false);
        txt7.setText("");
        txf7.setVisible(false);
    
    }

    @FXML
    private void btnAddClient(ActionEvent event) {
        cm.addClient(new Client(txf1.getText(), txf2.getText(), txf3.getText(), txf4.getText()));
    }

    @FXML
    private void btnSearch(ActionEvent event) {
        clean();
        if (cm.existClient(txfClientId.getText())) {

            txtClientData.setText(cm.getClient(txfClientId.getText()).toString());

            LinkedList<Account> list = am.getAccountsByClientId(txfClientId.getText());

            if (!list.isEmpty()) {

                ObservableList<Account> oL = FXCollections.observableArrayList();

                for (int i = 0; i < list.size(); i++) {
                    oL.add(list.get(i));
                }

                listAccounts.setItems(oL);

            } else {

                System.out.println("El cliente no tiene cuentas registradas.");

            }

        } else {

            txtClientData.setText("El Cliente buscado no existe.");

        }

    }

    @FXML
    private void rdbSavings(ActionEvent event) {
        txt1.setText("Tipo de moneda: (\'D\', \'C\', \'E\'...)");
        txf1.setVisible(true);
        txt2.setText("Deposito inicial: ");
        txf2.setVisible(true);
        txt3.setText("Tasa de interes: ");
        txf3.setVisible(true);
        rdbSavings.setSelected(true);
        rdbTerm.setSelected(false);
        
    }

    @FXML
    private void rdbTerm(ActionEvent event) {
        txt1.setText("Tipo de moneda: (\'D\', \'C\', \'E\'...)");
        txf1.setVisible(true);
        txt2.setText("Deposito inicial: ");
        txf2.setVisible(true);
        txt3.setText("Tasa de interes: ");
        txf3.setVisible(true);
        txt4.setText("Plazo : (Cantidad de meses)");
        txf4.setVisible(true);
        rdbSavings.setSelected(false);
        rdbTerm.setSelected(true);
    }

    @FXML
    private void btnSaveAccount(ActionEvent event) {
        try{
        if(rdbSavings.isSelected()){
            java.util.Date d = java.sql.Date.valueOf(java.time.LocalDate.now());
            am.addAccount(new SavingsAccount(txf1.getText().charAt(0), d, Float.parseFloat(txf2.getText()), txfClientId.getText(), Float.parseFloat(txf3.getText())));
        }else{
            java.util.Date d = java.sql.Date.valueOf(java.time.LocalDate.now());
            am.addAccount(new TermAccount(txf1.getText().charAt(0), d,txfClientId.getText() ,Float.parseFloat(txf2.getText()), Float.parseFloat(txf3.getText()), Integer.parseInt(txf4.getText())));
        }
        }catch(Exception e){
            System.out.println("Datos incorrectos");
        }
                
                
    }
    
}
