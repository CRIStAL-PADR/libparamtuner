/*
 *  libParamTuner
 *  Copyright (C) 2017 Marc Baloup, Veïs Oudjail
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.univ_lille1.libparamtuner.gui.parameters_panel;

import fr.univ_lille1.libparamtuner.gui.MainFrame;
import fr.univ_lille1.libparamtuner.parameters.FloatParameter;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class FloatParameterPanel extends ParameterPanel {
	
	public FloatParameterPanel(MainFrame f, int index, FloatParameter p) {
		super(f, index, p);
		
boolean minMaxValid = p.getMax() != p.getMin();
		
		double value = !minMaxValid ? p.getValue()
				: (p.getValue() < p.getMin()) ? ((long) p.getMin())
						: (p.getValue() > p.getMax()) ? ((long) p.getMax()) : p.getValue();
		
		Spinner<Double> spinner = new Spinner<>(
				minMaxValid ? p.getMin() : Long.MIN_VALUE,
				minMaxValid ? p.getMax() : Long.MAX_VALUE, value);
		spinner.setEditable(true);
		spinner.valueProperty().addListener((o, old, newValue) -> {
			p.setValue(newValue);
			notifyContentModification();
		});
		
		
		
		if (minMaxValid) {
			Slider slider = new Slider(p.getMin(), p.getMax(), value);
			slider.setShowTickMarks(false);
			slider.setShowTickLabels(false);
			spinner.getValueFactory().valueProperty().bindBidirectional(slider.valueProperty().asObject());
			add(slider);
			HBox.setHgrow(slider, Priority.ALWAYS);
		}
		
		add(spinner);
		
		
	}
	
	
}
