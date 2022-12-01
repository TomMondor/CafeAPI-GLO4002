package ca.ulaval.glo4002.cafe.api.customer.assembler;

import ca.ulaval.glo4002.cafe.api.customer.response.BillResponse;
import ca.ulaval.glo4002.cafe.service.customer.dto.BillDTO;

public class BillResponseAssembler {
    public BillResponse toBillResponse(BillDTO billDTO) {
        return new BillResponse(billDTO.coffees().stream().map(coffee -> coffee.coffeeType().toString()).toList(), billDTO.subtotal().getRoundedValue(),
            billDTO.taxes().getRoundedValue(), billDTO.tip().getRoundedValue(), billDTO.total().getRoundedValue());
    }
}
