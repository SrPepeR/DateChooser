package dad.javafx.componentes;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;

public class DateChooser extends HBox implements Initializable{
	
	@FXML
	ComboBox<Integer> dayCombo;
	@FXML
	ComboBox<Months> monthCombo;
	@FXML
	ComboBox<String> yearCombo;
	
	//MODEL
	private ObjectProperty<LocalDate> dateProperty = new SimpleObjectProperty<LocalDate>(LocalDate.MIN);
	
	public DateChooser() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/DateChooser.fxml"));
			loader.setController(this);
			loader.setRoot(this);
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		addYears();
		addMonths();
		yearCombo.getEditor().textProperty().addListener((o, ov, nv) -> isNumeric(ov, nv));
		monthCombo.getSelectionModel().selectedItemProperty().addListener((o, ov, nv) -> addDays(nv));
		dayCombo.getSelectionModel().selectedItemProperty().addListener(e -> setDate());
	}
	
	private void isNumeric(String ov, String nv) {
		char lastCharacter = nv.charAt(nv.length() - 1);
		if (!Character.isDigit(lastCharacter)) {
			yearCombo.getEditor().setText(ov);
		}
		if (Integer.parseInt(yearCombo.getEditor().getText()) >= 1900 && yearCombo.getEditor().getText().length() == 4) {
			setDate();
		}
	}

	private void addYears() {
		ArrayList<String> years = new ArrayList<String>();
		LocalDate date = LocalDate.now();
		int selectedYear = date.getYear();
		System.out.println(selectedYear);
		for (int i = selectedYear; i >= 1900; i--) {
			years.add(String.valueOf(i));
		}
		yearCombo.getItems().addAll(years);
		yearCombo.getSelectionModel().selectFirst();
	}
	
	private void addMonths() {
		Months[] months = {
				Months.ENERO,
				Months.FEBRERO,
				Months.MARZO,
				Months.ABRIL,
				Months.MAYO,
				Months.JUNIO,
				Months.JULIO,
				Months.AGOSTO,
				Months.SEPTIEMBRE,
				Months.OCTUBRE,
				Months.NOVIEMBRE,
				Months.DICIEMBRE
		};
		monthCombo.getItems().addAll(months);
		monthCombo.getSelectionModel().selectFirst();
		addDays(monthCombo.getSelectionModel().getSelectedItem());
	}
	
	private void addDays(Months nv) {
		dayCombo.getItems().clear();
		for (int i = 1; i <= 28 ; i++) {
			dayCombo.getItems().add(i);
		}
		Months selectedMonth = nv;
		int selectedMonthNumber = nv.getNumber();
		if (selectedMonthNumber == 2) {
			Boolean bisiesto = Year.of(selectedMonthNumber).isLeap(); // devuelve "true" si es bisiesto, y "false" en caso contrario
			if (bisiesto) {
				dayCombo.getItems().add(29);
			}
		}else {
			dayCombo.getItems().add(29);
			dayCombo.getItems().add(30);
			if (selectedMonth.equals(Months.ENERO) || selectedMonth.equals(Months.MARZO) || 
					selectedMonth.equals(Months.MAYO) || selectedMonth.equals(Months.JULIO) || 
					selectedMonth.equals(Months.AGOSTO) || selectedMonth.equals(Months.OCTUBRE) || 
					selectedMonth.equals(Months.DICIEMBRE)) {
				dayCombo.getItems().add(31);
			}
		}
		dayCombo.getSelectionModel().selectFirst();
		setDate();
	}
	
	private void setDate() {
		try {
			int year = Integer.parseInt(yearCombo.getEditor().getText());
			int month = monthCombo.getSelectionModel().getSelectedItem().getNumber();
			int day = dayCombo.getSelectionModel().getSelectedItem();
			this.dateProperty.set(LocalDate.of(year, month, day));
			System.out.println(getDateProperty());
		} catch (Exception e) {
		}
	}
	
	public void setNowDate() {
		LocalDate now = LocalDate.now();
		this.yearCombo.getSelectionModel().select(Integer.parseInt(yearCombo.getItems().get(0)) - now.getYear());
		this.monthCombo.getSelectionModel().select(now.getMonthValue() - 1);
		this.dayCombo.getSelectionModel().select(now.getDayOfMonth() - 1);
	}
	
	public final ObjectProperty<LocalDate> datePropertyProperty() {
		return this.dateProperty;
	}
	

	public final LocalDate getDateProperty() {
		return this.datePropertyProperty().get();
	}
	

	public final void setDateProperty(final LocalDate dateProperty) {
		this.datePropertyProperty().set(dateProperty);
	}
	
	public ComboBox<Integer> getDaysCombo(){
		return this.dayCombo;
	}
	
	public ComboBox<Months> getMonthsCombo(){
		return this.monthCombo;
	}
	
	public ComboBox<String> getYearsCombo(){
		return this.yearCombo;
	}
	
	
	public enum Months{
		ENERO(1, "Enero"),
		FEBRERO(2, "Febrero"),
		MARZO(3, "Marzo"),
		ABRIL(4, "Abril"),
		MAYO(5, "Mayo"),
		JUNIO(6, "Junio"),
		JULIO(7, "Julio"),
		AGOSTO(8, "Agosto"),
		SEPTIEMBRE(9, "Septiembre"),
		OCTUBRE(10, "Octubre"),
		NOVIEMBRE(11, "Noviembre"),
		DICIEMBRE(12, "Diciembre");
		
		private int numberOfMonth;
		private String nameOfMonth;
		
		private Months(int number, String name) {
			this.numberOfMonth = number;
			this.nameOfMonth = name;
		}
		
		public int getNumber() {
			return this.numberOfMonth;
		}
		public String getName() {
			return this.nameOfMonth;
		}
		
	}
	
}