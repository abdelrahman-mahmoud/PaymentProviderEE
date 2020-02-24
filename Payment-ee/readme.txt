Implementation of a simple payment provider. This “simulated” provider works only with
the request data, its own persisted data and the business rules defined in this document,
no external payment system is contacted.

To be able to run this project after build through the command line, Please follow the below sample commands:

1. To register a new order in REGISTER status, please run the jar and send the below parameters:
	register clientId="IBEX" orderId="book-37843X" amount="123" currency="EUR" payMethod="Card" payTokenId="pp-32f45c932d776X"
	
	
2. To Authorise an order by performing an authorisation transaction, please run the jar and send the below parameters:
	authorise clientId="IBEX" orderId="book-37843X"
	
	
3. To Capture an order by performing a capture transaction, please run the jar and send the below parameters:
	capture clientId="IBEX" orderId="book-37843X"
	

4. To reverse an order by performing a reverse transaction, please run the jar and send the below parameters:
	reverse clientId="IBEX" orderId="book-37843X"

5. To be able to retrieve a specific order id for a specific client id, , please run the jar and send the below parameters:
	findByOrder clientId="IBEX" orderId="book-37843X"
	
6. To be able to retrieve all pending order(s) for a specific client id , please run the jar and send the below parameters:
	findPending clientId="IBEX"
	
7. 
	totalAmountOfSuccessPayments clientId="C-ID-4" fromDate="2020-02-21" toDate="2020-02-24"

