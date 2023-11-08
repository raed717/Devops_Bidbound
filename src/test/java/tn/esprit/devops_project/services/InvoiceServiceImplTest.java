package tn.esprit.devops_project.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.devops_project.entities.Invoice;
import tn.esprit.devops_project.entities.Operator;
import tn.esprit.devops_project.entities.Supplier;
import tn.esprit.devops_project.repositories.InvoiceRepository;
import tn.esprit.devops_project.repositories.OperatorRepository;
import tn.esprit.devops_project.repositories.SupplierRepository;
import tn.esprit.devops_project.services.InvoiceServiceImpl;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class InvoiceServiceImplTest {

    @InjectMocks
    private InvoiceServiceImpl invoiceService;

    @Mock
    private InvoiceRepository invoiceRepository;

    @Mock
    private OperatorRepository operatorRepository;

    @Mock
    private SupplierRepository supplierRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRetrieveAllInvoices() {
        List<Invoice> invoiceList = new ArrayList<>();
        when(invoiceRepository.findAll()).thenReturn(invoiceList);

        List<Invoice> result = invoiceService.retrieveAllInvoices();

        assertEquals(invoiceList, result);
    }

    @Test
    public void testCancelInvoice() {
        Long invoiceId = 1L;
        Invoice invoice = new Invoice();
        when(invoiceRepository.findById(invoiceId)).thenReturn(Optional.of(invoice));

        invoiceService.cancelInvoice(invoiceId);

        assertTrue(invoice.isArchived());
        verify(invoiceRepository, times(1)).save(invoice);
        verify(invoiceRepository, times(1)).updateInvoice(invoiceId);
    }

    @Test
    public void testRetrieveInvoice() {
        Long invoiceId = 1L;
        Invoice invoice = new Invoice();
        when(invoiceRepository.findById(invoiceId)).thenReturn(Optional.of(invoice));

        Invoice result = invoiceService.retrieveInvoice(invoiceId);

        assertEquals(invoice, result);
    }

    @Test
    public void testGetInvoicesBySupplier() {
        Long idSupplier = 1L;
        Supplier supplier = new Supplier();
        when(supplierRepository.findById(idSupplier)).thenReturn(Optional.of(supplier));

        List<Invoice> invoices = new ArrayList<>(); // Add some invoices to the supplier here
        when(supplier.getInvoices()).thenReturn((Set<Invoice>) invoices);

        List<Invoice> result = invoiceService.getInvoicesBySupplier(idSupplier);

        assertEquals(invoices, result);
    }

    @Test
    public void testAssignOperatorToInvoice() {
        Long idOperator = 1L;
        Long idInvoice = 2L;
        Invoice invoice = new Invoice();
        Operator operator = new Operator();
        when(invoiceRepository.findById(idInvoice)).thenReturn(Optional.of(invoice));
        when(operatorRepository.findById(idOperator)).thenReturn(Optional.of(operator));

        invoiceService.assignOperatorToInvoice(idOperator, idInvoice);

        assertTrue(operator.getInvoices().contains(invoice));
        verify(operatorRepository, times(1)).save(operator);
    }

    @Test
    public void testGetTotalAmountInvoiceBetweenDates() {
        Date startDate = new Date();
        Date endDate = new Date();
        float totalAmount = 100.0f;
        when(invoiceRepository.getTotalAmountInvoiceBetweenDates(startDate, endDate)).thenReturn(totalAmount);

        float result = invoiceService.getTotalAmountInvoiceBetweenDates(startDate, endDate);

        assertEquals(totalAmount, result, 0.0f);
    }
}
