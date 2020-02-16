import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import model.beans.inserzioni.IntervalloDisponibilit‡Bean;
import model.dataAccessObjects.inserzioni.IntervalloDisponibilit‡Dao;
import model.dataAccessObjects.inserzioni.IntervalloDisponibilit‡DaoImpl;

public class Test {
	public static void main(String[] args) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		long idInserzione = 1;
		Date checkInDate = format.parse("2021-01-04");
		Date checkOutDate = format.parse("2021-01-08");
		
		IntervalloDisponibilit‡Dao idd = new IntervalloDisponibilit‡DaoImpl();
		IntervalloDisponibilit‡Bean idb = idd.doRetrieveByDate(1, checkInDate, checkOutDate);
		
		idd.doDelete(idInserzione, idb.getDataInizio(), idb.getDataFine());
		
		Calendar c = Calendar.getInstance();
		c.setTime(checkInDate);
		c.add(Calendar.DATE, 0);
		
		IntervalloDisponibilit‡Bean id1 = new IntervalloDisponibilit‡Bean();
		id1.setIdInserzione(idInserzione);
		id1.setDataInizio(idb.getDataInizio());
		id1.setDataFine(c.getTime());
		idd.doSave(id1);
		
		c = Calendar.getInstance();
		c.setTime(checkOutDate);
		c.add(Calendar.DATE, 2);
		IntervalloDisponibilit‡Bean id2 = new IntervalloDisponibilit‡Bean();
		id2.setIdInserzione(idInserzione);
		id2.setDataInizio(c.getTime());
		id2.setDataFine(idb.getDataFine());
		idd.doSave(id2);
		
		List<IntervalloDisponibilit‡Bean> intervalli = idd.doRetrieveByIdInserzione(1);
		for(IntervalloDisponibilit‡Bean i : intervalli) {
			System.out.println(i.getDataInizio() + "  ///  " + i.getDataFine());	
		}

	}
}
