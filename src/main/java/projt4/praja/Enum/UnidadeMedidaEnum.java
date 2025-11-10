package projt4.praja.Enum;

import java.util.Optional;

public enum UnidadeMedidaEnum {
    UNIDADE(0, "UN"),
    MILIGRAMA(1, "G"),
    MILILITRO(2, "ML");

		UnidadeMedidaEnum(int numero, String nome) {
				this.numero = numero;
				this.nome = nome;
		}

		private int numero;
    private String nome;

    public int getNumero() {
        return numero;
    }

    public String getNome() {
        return nome;
    }

		public static Optional<String> getString(int number) {
				for (UnidadeMedidaEnum unidade : UnidadeMedidaEnum.values()) {
						if (unidade.getNumero() == number) {
								return Optional.of(unidade.getNome());
						}
				}
				return Optional.empty();
		}
}
