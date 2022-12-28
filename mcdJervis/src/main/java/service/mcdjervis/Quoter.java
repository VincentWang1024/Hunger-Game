package service.mcdjervis;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.core.AbstractQuotationService;
import service.core.Quotation;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Implementation of the AuldFellas insurance quotation service.
 * 
 * @author Rem
 *
 */

@RestController
public class Quoter extends AbstractQuotationService {

	public static void main(String[] args) {
	}

	private Map<String, Quotation> quotations = new HashMap<>();

	@RequestMapping(value="/quotations",method= RequestMethod.POST)
	public ResponseEntity<Quotation> createQuotation() throws URISyntaxException {
		Quotation quotation = generateQuotation();
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<>(quotation, headers, HttpStatus.CREATED);
	}

	@RequestMapping(value="/quotations/{reference}",method=RequestMethod.GET)
	public Quotation getResource(@PathVariable("reference") String reference) {
		Quotation quotation = quotations.get(reference);
		if (quotation == null) throw new NoSuchQuotationException();
		return quotation;
	}

	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public class NoSuchQuotationException extends RuntimeException {
		static final long serialVersionUID = -6516152229878843037L;
	}

	/**
	 * Quote generation:
	 * 30% discount for being male
	 * 2% discount per year over 60
	 * 20% discount for less than 3 penalty points
	 * 50% penalty (i.e. reduction in discount) for more than 60 penalty points 
	 */
	private Quotation generateQuotation() {
		// Generate the quotation and send it back
		return new Quotation("mcdJervis", new Random().nextInt(20));
	}

}
