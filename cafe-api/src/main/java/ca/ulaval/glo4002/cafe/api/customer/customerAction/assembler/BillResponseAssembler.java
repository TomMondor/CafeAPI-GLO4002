package ca.ulaval.glo4002.cafe.api.customer.customerAction.assembler;

import ca.ulaval.glo4002.cafe.api.customer.customerAction.response.BillResponse;
import ca.ulaval.glo4002.cafe.application.customer.dto.BillDTO;

public class BillResponseAssembler {
    public BillResponse toBillResponse(BillDTO billDTO) {
        return new BillResponse(billDTO.coffees().stream().map(coffee -> coffee.name().value()).toList(), billDTO.subtotal().getRoundedValue(),
            billDTO.taxes().getRoundedValue(), billDTO.tip().getRoundedValue(), billDTO.total().getRoundedValue());
    }
}
