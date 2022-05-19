package dds.monedero.model;

import java.time.LocalDate;

public abstract class Movimiento {

  private LocalDate fecha;
  private Double monto;

  public Movimiento(LocalDate fecha, Double monto) {
    this.fecha = fecha;
    this.monto = monto;
  }

  public Double getMonto() {
    return monto;
  }

  public abstract Boolean fueDepositadoEn(LocalDate fecha);

  public abstract Boolean fueExtraidoEn(LocalDate fecha);

  public Boolean esDeLaFecha(LocalDate fecha) {
    return this.fecha.equals(fecha);
  }

}
