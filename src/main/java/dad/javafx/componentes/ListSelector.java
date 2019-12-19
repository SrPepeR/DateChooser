package dad.javafx.componentes;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.GridPane;

public class ListSelector<T> extends GridPane implements Initializable {
	
    @FXML
    private Label leftLabel;

    @FXML
    private Label rightLabel;

    @FXML
    private ListView<T> leftList;

    @FXML
    private Button rightButton;

    @FXML
    private Button allRightButton;

    @FXML
    private Button allLeftButton;

    @FXML
    private Button leftButton;

    @FXML
    private ListView<T> rightList;
    
    //MODEL
    private ListProperty<T> left = new SimpleListProperty<>(FXCollections.observableArrayList());
    private ListProperty<T> right = new SimpleListProperty<>(FXCollections.observableArrayList());
	private StringProperty leftTittle = new SimpleStringProperty();
	private StringProperty rightTittle = new SimpleStringProperty();
    
	public ListSelector() {
		super();
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/ListSelectorView.fxml"));
			loader.setController(this);
			loader.setRoot(this);		//Establecemos la raíz de la vista.
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		leftList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		rightList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		leftList.itemsProperty().bind(left);
		rightList.itemsProperty().bind(right);
		
		leftLabel.textProperty().bind(leftTittle);
		rightLabel.textProperty().bind(rightTittle);
	}
	
    @FXML
    void moveAllToLeft(ActionEvent event) {
    	left.addAll(right);
    	right.clear();
    }

    @FXML
    void moveAllToRight(ActionEvent event) {
    	right.addAll(left);
    	left.clear();
    }

    @FXML
    void moveToLeft(ActionEvent event) {
    	left.addAll(rightList.getSelectionModel().getSelectedItems());
    	right.removeAll(rightList.getSelectionModel().getSelectedItems());
    }

    @FXML
    void moveToRight(ActionEvent event) {
    	right.addAll(leftList.getSelectionModel().getSelectedItems());
    	left.removeAll(leftList.getSelectionModel().getSelectedItems());
    }

	public final ListProperty<T> leftProperty() {
		return this.left;
	}
	

	public final ObservableList<T> getLeft() {
		return this.leftProperty().get();
	}
	

	public final void setLeft(final ObservableList<T> left) {
		this.leftProperty().set(left);
	}
	

	public final ListProperty<T> rightProperty() {
		return this.right;
	}
	

	public final ObservableList<T> getRight() {
		return this.rightProperty().get();
	}
	

	public final void setRight(final ObservableList<T> right) {
		this.rightProperty().set(right);
	}
	

	public final StringProperty leftTittleProperty() {
		return this.leftTittle;
	}
	

	public final String getLeftTittle() {
		return this.leftTittleProperty().get();
	}
	

	public final void setLeftTittle(final String leftTittle) {
		this.leftTittleProperty().set(leftTittle);
	}
	

	public final StringProperty rightTittleProperty() {
		return this.rightTittle;
	}
	

	public final String getRightTittle() {
		return this.rightTittleProperty().get();
	}
	

	public final void setRightTittle(final String rightTittle) {
		this.rightTittleProperty().set(rightTittle);
	}

}
