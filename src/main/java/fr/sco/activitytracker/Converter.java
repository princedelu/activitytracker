package fr.sco.activitytracker;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.camel.Exchange;
import org.apache.camel.component.file.GenericFile;

import fr.sco.activitytracker.exception.ExecutorException;

public class Converter {

	public void exec(Exchange exchange) {

		Object bodyObject = exchange.getIn().getBody();

		try {

			if (bodyObject instanceof GenericFile<?>) {
				String resultString = "";
				GenericFile<?> gf = (GenericFile<?>) bodyObject;
				BufferedReader br = null;
				String sCurrentLine;

				try {
					br = new BufferedReader(new FileReader(gf.getAbsoluteFilePath()));
				} catch (FileNotFoundException e) {
					throw new ExecutorException("Le fichier a convertir n a pas ete trouve");
				}

				try {
					while ((sCurrentLine = br.readLine()) != null) {
						resultString += sCurrentLine;
					}
				} catch (IOException e) {
					throw new ExecutorException("Problème de lecture du fichier");
				}finally {
					try {
						if (br != null)br.close();
					} catch (IOException ex) {
						throw new ExecutorException("Problème de fermeture du fichier");
					}
				}
				exchange.getOut().setBody(resultString.getBytes());
			}

		} catch (ExecutorException ee) {

		}
	}

}
