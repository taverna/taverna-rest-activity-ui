package net.sf.taverna.t2.activities.rest.ui.visit;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

import net.sf.taverna.t2.activities.rest.RESTActivityHealthCheck;
import net.sf.taverna.t2.lang.ui.ReadOnlyTextArea;
import net.sf.taverna.t2.visit.VisitKind;
import net.sf.taverna.t2.visit.VisitReport;
import net.sf.taverna.t2.workbench.report.explainer.VisitExplainer;
import net.sf.taverna.t2.workbench.report.view.ReportViewConfigureAction;
import net.sf.taverna.t2.workflowmodel.Processor;
import static java.awt.Component.LEFT_ALIGNMENT;
import static javax.swing.BoxLayout.Y_AXIS;
// import status constants
import static net.sf.taverna.t2.activities.rest.RESTActivityHealthCheck.*;

/**
 * 
 * @author Sergejs Aleksejevs
 */
public class RESTActivityHealthCheckVisitExplainer implements VisitExplainer {
	@Override
	public boolean canExplain(VisitKind vk, int resultId) {
		return vk instanceof RESTActivityHealthCheck;
	}

	/**
	 * This class only handles {@link VisitReport} instances that are of
	 * {@link RESTActivityHealthCheck} kind. Therefore, decisions on the
	 * explanations / solutions are made solely by visit result IDs.
	 */
	@Override
	public JComponent getExplanation(VisitReport vr) {
		String explanation;

		switch (vr.getResultId()) {
		case CORRECTLY_CONFIGURED:
			explanation = "No problem found";
			break;

		case GENERAL_CONFIG_PROBLEM:
			explanation = "Configuration of this REST activity is not valid";
			break;

		default:
			explanation = "Unknown issue - no expalanation available";
			break;
		}

		return new ReadOnlyTextArea(explanation);
	}

	/**
	 * This class only handles {@link VisitReport} instances that are of
	 * {@link RESTActivityHealthCheck} kind. Therefore, decisions on the
	 * explanations / solutions are made solely by visit result IDs.
	 */
	@Override
	public JComponent getSolution(VisitReport vr) {
		String solution;
		boolean includeConfigButton = false;

		switch (vr.getResultId()) {
		case CORRECTLY_CONFIGURED:
			solution = "No change necessary";
			break;

		case GENERAL_CONFIG_PROBLEM:
			solution = "Please check configuration of this REST activity:";
			includeConfigButton = true;
			break;

		default:
			solution = "Unknown issue - no solution available";
			break;
		}

		JPanel jpSolution = new JPanel();
		jpSolution.setLayout(new BoxLayout(jpSolution, Y_AXIS));

		ReadOnlyTextArea taSolution = new ReadOnlyTextArea(solution);
		taSolution.setAlignmentX(LEFT_ALIGNMENT);
		jpSolution.add(taSolution);

		if (includeConfigButton) {
			JButton button = new JButton(
					"Open REST Activity configuration dialog");
			button.setAction(new ReportViewConfigureAction((Processor) vr
					.getSubject()));
			button.setAlignmentX(LEFT_ALIGNMENT);
			jpSolution.add(button);
		}

		return jpSolution;
	}
}
