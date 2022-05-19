package dds.monedero.model;

import java.time.LocalDate;

public class MovimientoExtraccion extends Movimiento {

  public MovimientoExtraccion(LocalDate now, Double unMonto) {
    super(now, unMonto);
  }

  @Override
  public Boolean fueDepositadoEn(LocalDate fecha) {
    return false;
  }

  @Override
  public Boolean fueExtraidoEn(LocalDate fecha) {
    return this.esDeLaFecha(fecha);
  }

}
