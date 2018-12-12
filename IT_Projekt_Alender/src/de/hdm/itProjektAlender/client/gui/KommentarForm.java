package de.hdm.itProjektAlender.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import de.hdm.itProjektAlender.client.ClientsideSettings;
import de.hdm.itProjektAlender.shared.SocialMediaAdminAsync;
import de.hdm.itProjektAlender.shared.bo.Kommentar;
import de.hdm.itProjektAlender.shared.bo.Nutzer;

public class KommentarForm extends FlexTable {

	private Label text = new Label();
	private Label datum = new Label();
	private Label nickname = new Label();
	private Button edit = new Button("Edit");
	int id;
	boolean pinn;

	private SocialMediaAdminAsync socialMedia = ClientsideSettings.getSocialMediaAdmin();

	public KommentarForm(final Kommentar kommentar, int nutzer, boolean pinn) {

		final int rowCount = this.getRowCount();
		id = nutzer;
		this.pinn = pinn;

		socialMedia.findNutzerById(kommentar.getErsteller_Id(), new AsyncCallback<Nutzer>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(Nutzer result) {
				nickname.setText(result.getNickname());
				text.setText(kommentar.getText());
				datum.setText(
						DateTimeFormat.getFormat("dd.MM.yyyy HH:mm").format(kommentar.getErstellungszeitpunkt()));

				setWidget(rowCount, 0, datum);
				setWidget(rowCount, 1, nickname);
				setWidget(rowCount + 1, 0, text);

				if (kommentar.getErsteller_Id() == id) {
					edit.setStylePrimaryName("like-btn");
					setWidget(rowCount + 1, 1, edit);
					edit.addClickHandler(new EditClickhandler(kommentar.getId()));
				}

			}
		});

	}

	public class EditClickhandler implements ClickHandler {

		int id;

		public EditClickhandler(int beitragId) {
			id = beitragId;
		}

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub

			new DialogBoxEditKommentar(id, pinn);
		}

	}

}
