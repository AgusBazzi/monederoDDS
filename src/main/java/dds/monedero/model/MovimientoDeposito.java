package dds.monedero.model;

import java.time.LocalDate;

public class MovimientoDeposito extends Movimiento {

  public MovimientoDeposito(LocalDate now, Double unMonto) {
    super(now, unMonto);
  }

  @Override
  public Boolean fueDepositadoEn(LocalDate fecha) {
    return this.esDeLaFecha(fecha);
  }

  @Override
  public Boolean fueExtraidoEn(LocalDate fecha) {
    return false;
  }

}
