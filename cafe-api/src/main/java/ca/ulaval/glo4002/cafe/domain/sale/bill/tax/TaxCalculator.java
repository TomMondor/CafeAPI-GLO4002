package ca.ulaval.glo4002.cafe.domain.sale.bill.tax;

import java.util.Optional;

import ca.ulaval.glo4002.cafe.domain.Country;
import ca.ulaval.glo4002.cafe.domain.Location;
import ca.ulaval.glo4002.cafe.domain.Province;
import ca.ulaval.glo4002.cafe.domain.State;

public class TaxCalculator {
    public Tax calculateTaxPercentage(Location location) {
        Tax taxPercentage = calculateCountryTax(location.country());
        taxPercentage = taxPercentage.add(calculateProvinceTax(location.province()));
        taxPercentage = taxPercentage.add(calculateStateTax(location.state()));

        return taxPercentage;
    }

    private Tax calculateCountryTax(Country country) {
        return switch (country) {
            case CA -> new Tax(0.05f);
            case US -> new Tax(0);
            case CL -> new Tax(0.19f);
            case None -> new Tax(0);
        };
    }

    private Tax calculateProvinceTax(Optional<Province> province) {
        if (province.isEmpty()) {
            return new Tax(0);
        }
        return switch (province.get()) {
            case AB -> new Tax(0);
            case BC -> new Tax(0.07f);
            case MB -> new Tax(0.07f);
            case NB -> new Tax(0.10f);
            case NL -> new Tax(0.10f);
            case NT -> new Tax(0);
            case NS -> new Tax(0.10f);
            case NU -> new Tax(0);
            case ON -> new Tax(0.08f);
            case PE -> new Tax(0.10f);
            case QC -> new Tax(0.09975f);
            case SK -> new Tax(0.06f);
            case YT -> new Tax(0);
        };
    }

    private Tax calculateStateTax(Optional<State> state) {
        if (state.isEmpty()) {
            return new Tax(0);
        }
        return switch (state.get()) {
            case AL -> new Tax(0.04f);
            case AZ -> new Tax(0.056f);
            case CA -> new Tax(0.0725f);
            case FL -> new Tax(0.06f);
            case ME -> new Tax(0.055f);
            case NY -> new Tax(0.04f);
            case TX -> new Tax(0.0625f);
        };
    }
}
