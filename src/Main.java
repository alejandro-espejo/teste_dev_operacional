import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

	public static final Locale localeBR = new Locale("pt", "BR");
	public static final Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {

		// SIMULANDO BANCO DE DADOS

		List<Produto> carrinho = new ArrayList<Produto>();
		List<Venda> vendas = new ArrayList<Venda>();

		Empresa empresa = new Empresa(2, "SafeWay Padaria", "30021423000159", 0.15, 0.0);
		Empresa empresa2 = new Empresa(1, "Level Varejo", "53239160000154", 0.05, 0.0);
		Empresa empresa3 = new Empresa(3, "SafeWay Restaurante", "41361511000116", 0.20, 0.0);

		Produto produto = new Produto(1, "Pão Frances", 5, 3.50, empresa);
		Produto produto2 = new Produto(2, "Coturno", 10, 400.0, empresa2);
		Produto produto3 = new Produto(3, "Jaqueta Jeans", 15, 150.0, empresa2);
		Produto produto4 = new Produto(4, "Calça Sarja", 15, 150.0, empresa2);
		Produto produto5 = new Produto(5, "Prato feito - Frango", 10, 25.0, empresa3);
		Produto produto6 = new Produto(6, "Prato feito - Carne", 10, 25.0, empresa3);
		Produto produto7 = new Produto(7, "Suco Natural", 30, 10.0, empresa3);
		Produto produto8 = new Produto(8, "Sonho", 5, 8.50, empresa);
		Produto produto9 = new Produto(9, "Croissant", 7, 6.50, empresa);
		Produto produto10 = new Produto(10, "Ché Gelado", 4, 5.50, empresa);

		Cliente cliente = new Cliente("07221134049", "Allan da Silva", "cliente", 20);
		Cliente cliente2 = new Cliente("72840700050", "Samuel da Silva", "cliente2", 24);

		Usuario usuario1 = new Usuario("admin", "1234", null, null);
		Usuario usuario2 = new Usuario("empresa", "1234", null, empresa);
		Usuario usuario3 = new Usuario("cliente", "1234", cliente, null);
		Usuario usuario4 = new Usuario("cliente2", "1234", cliente2, null);
		Usuario usuario5 = new Usuario("empresa2", "1234", null, empresa2);
		Usuario usuario6 = new Usuario("empresa3", "1234", null, empresa3);

		List<Usuario> usuarios = Arrays.asList(usuario1, usuario2, usuario3, usuario4, usuario5, usuario6);
		List<Cliente> clientes = Arrays.asList(cliente, cliente2);
		List<Empresa> empresas = Arrays.asList(empresa, empresa2, empresa3);
		List<Produto> produtos = Arrays.asList(produto, produto2, produto3, produto4, produto5, produto6, produto7,
				produto8, produto9, produto10);

		while (true) {
			System.out.println("Efetuar login? [S]im ou [N]ão: ");
			System.out.println("");
			String opcao = sc.next();
			if (opcao.equalsIgnoreCase("N")) {
				System.out.println("Sessão encerrada.");
				break;
			} else if (opcao.equalsIgnoreCase("S")) {
				executar(usuarios, clientes, empresas, produtos, carrinho, vendas);
			} else {
				System.out.println("Opção inválida.");
			}
		}
		sc.close();
	}

	public static void executar(List<Usuario> usuarios, List<Cliente> clientes, List<Empresa> empresas,
			List<Produto> produtos, List<Produto> carrinho, List<Venda> vendas) {
		System.out.println("Entre com seu usuário e senha:");
		System.out.print("Usuário: ");
		String username = sc.next();
		System.out.print("Senha: ");
		String senha = sc.next();

		List<Usuario> usuariosSearch = usuarios.stream().filter(x -> x.getUsername().equals(username))
				.collect(Collectors.toList());
		if (usuariosSearch.size() > 0) {
			Usuario usuarioLogado = usuariosSearch.get(0);
			if ((usuarioLogado.getSenha().equals(senha))) {
				System.out.println("Escolha uma opção para iniciar");
				if (usuarioLogado.IsAdmin()) {
					menuAdmin(produtos, carrinho, empresas, clientes, usuarios, vendas);
				} else if (usuarioLogado.IsEmpresa()) {
					menuEmpresa(produtos, carrinho, empresas, clientes, usuarios, usuarioLogado.getEmpresa(), vendas);
				} else {
					menuCliente(produtos, carrinho, empresas, clientes, usuarios, usuarioLogado, vendas);
				}
			} else {
				System.out.println("Senha incorreta");
			}
		} else {
			System.out.println("Usuário não encontrado");
		}
	}

	public static void menuAdmin(List<Produto> produtos, List<Produto> carrinho, List<Empresa> empresas,
			List<Cliente> clientes, List<Usuario> usuarios, List<Venda> vendas) {
		Integer escolha = -1;
		while (escolha != 0) {
			System.out.println("1 - Escolher Empresa");
			System.out.println("0 - Deslogar");
			if (sc.hasNextInt()) {
				escolha = sc.nextInt();
				if (escolha.equals(1)) {
					empresas.stream().forEach(x -> {
						System.out.println(x.getId() + " - " + x.getNome());
					});
					System.out.println("Digite o id da Empresa: ");
					Integer idEmpresa = -1;
					if (sc.hasNextInt()) {
						idEmpresa = sc.nextInt();
						if (!buscaEmpresa(empresas, idEmpresa)) {
							System.out.println("Empresa não encontrada.");
							idEmpresa = -1;
						} else {
							Empresa empresa = buscaEmpresaObjeto(empresas, idEmpresa);
							menuEmpresa(produtos, carrinho, empresas, clientes, usuarios, empresa, vendas);
						}
					} else {
						System.out.println("Código de empresa inválido.");
					}
				} else if (escolha.equals(0)) {
					executar(usuarios, clientes, empresas, produtos, carrinho, vendas);
				}
			} else {
				System.out.println("Código inválido.");
				sc.next();
			}
		}
	}

	public static void menuCliente(List<Produto> produtos, List<Produto> carrinho, List<Empresa> empresas,
			List<Cliente> clientes, List<Usuario> usuarios, Usuario usuarioLogado, List<Venda> vendas) {
		Integer escolha = -1;
		while (escolha != 0) {
			System.out.println("1 - Realizar Compras");
			System.out.println("2 - Ver Compras");
			System.out.println("0 - Sair");
			if (sc.hasNextInt()) {
				escolha = sc.nextInt();
				switch (escolha) {
				case 1: {
					escolheEmpresaParaCompra(produtos, carrinho, escolha, empresas, clientes, usuarioLogado, vendas);
					break;
				}
				case 2: {
					exibirComprasEfetuadas(vendas, usuarioLogado);
					break;
				}
				case 0: {
					break;
				}
				default: {
					System.out.println("Opção inválida.");
					break;
				}
				}
			} else {
				System.out.println("Código inválido.");
				sc.next();
			}
		}
	}

	public static void menuEmpresa(List<Produto> produtos, List<Produto> carrinho, List<Empresa> empresas,
			List<Cliente> clientes, List<Usuario> usuarios, Empresa empresa, List<Venda> vendas) {
		Integer escolha = -1;
		while (escolha != 0) {
			System.out.println("1 - Listar vendas");
			System.out.println("2 - Ver produtos");
			System.out.println("0 - Sair");
			if (sc.hasNextInt()) {
				escolha = sc.nextInt();
				switch (escolha) {
				case 1: {
					exibirVendasEfetuadas(vendas, empresa);
					break;
				}
				case 2: {
					listarMeusProdutos(produtos, empresa);
					break;
				}
				case 0: {
					break;
				}
				default: {
					System.out.println("Opção inválida.");
					break;
				}
				}
			} else {
				System.out.println("Código inválido.");
				sc.next();
			}
		}
	}

	public static Venda criarVenda(List<Produto> carrinho, Empresa empresa, Cliente cliente, List<Venda> vendas) {
		Double total = carrinho.stream().mapToDouble(Produto::getPreco).sum();
		Double comissaoSistema = total * empresa.getTaxa();
		int idVenda = vendas.isEmpty() ? 1 : vendas.get(vendas.size() - 1).getCódigo() + 1;
		Venda venda = new Venda(idVenda, carrinho.stream().toList(), total, comissaoSistema, empresa, cliente);
		empresa.setSaldo(empresa.getSaldo() + total);
		vendas.add(venda);
		return venda;
	}

	public static boolean buscaEmpresa(List<Empresa> empresas, Integer codigo) {
		return empresas.stream().filter(o -> o.getId().equals(codigo)).findFirst().isPresent();
	}

	public static Empresa buscaEmpresaObjeto(List<Empresa> empresas, Integer codigo) {
		return empresas.stream().filter(o -> o.getId().equals(codigo)).findFirst().orElse(null);
	}

	public static void escolheEmpresaParaCompra(List<Produto> produtos, List<Produto> carrinho, Integer codigoEmpresa,
			List<Empresa> empresas, List<Cliente> clientes, Usuario usuarioLogado, List<Venda> vendas) {
		System.out.println("Para realizar uma compra, escolha a empresa onde deseja comprar: ");
		empresas.stream().forEach(x -> {
			System.out.println(x.getId() + " - " + x.getNome());
		});
		if (sc.hasNextInt()) {
			Integer escolhaEmpresa = sc.nextInt();
			boolean empresaEncontrada = buscaEmpresa(empresas, escolhaEmpresa);
			if (empresaEncontrada) {
				int empresa = escolhaEmpresa;
				carrinho = realizaCompra(produtos, escolhaEmpresa);
				exibirResumoCompra(carrinho, empresa, empresas, clientes, usuarioLogado, vendas);
				carrinho.clear();
			} else {
				System.out.println("Empresa não encontrada.");
			}
		} else {
			System.out.println("Código de produto inválido.");
			sc.next();
		}
	}

	public static List<Produto> realizaCompra(List<Produto> produtos, int escolhaEmpresa) {
		List<Produto> carrinho = new ArrayList<>();
		Integer escolhaProduto = -1;
		do {
			System.out.println("Escolha os seus produtos: ");
			produtos.stream().forEach(x -> {
				if (x.getEmpresa().getId().equals(escolhaEmpresa)) {
					System.out.printf(localeBR, "%-4s - %-25s - R$ %.2f%n", x.getId(), x.getNome(), x.getPreco());
				}
			});
			System.out.printf("%-4s - Finalizar compra%n", "0");
			if (sc.hasNextInt()) {
				escolhaProduto = sc.nextInt();
				int codigoProduto = escolhaProduto;
				Produto produtoSearch = produtos.stream()
						.filter(o -> o.getId().equals(codigoProduto) && o.getEmpresa().getId() == escolhaEmpresa)
						.findFirst().orElse(null);
				if (produtoSearch != null) {
					if (produtoComEstoque(codigoProduto, produtos)) {
						carrinho.add(produtoSearch);
						atualizaQuantidadeProduto(produtoSearch, produtos);
					} else {
						System.out.println("Produto não disponível em estoque.");
					}
				} else {
					System.out.println("Produto não encontrado.");
				}
			} else {
				System.out.println("Código de produto inválido.");
				sc.next();
			}
		} while (escolhaProduto != 0);
		return carrinho;
	}

	public static boolean produtoComEstoque(int codigoProduto, List<Produto> produtos) {
		Integer quantidade = produtos.stream().filter(o -> o.getId() == codigoProduto).mapToInt(Produto::getQuantidade)
				.findFirst().orElse(0);
		if (quantidade <= 0) {
			return false;
		}
		return true;
	}

	public static List<Produto> atualizaQuantidadeProduto(Produto produtoEscolhido, List<Produto> produtos) {
		for (Produto produto : produtos) {
			if (produto.getId().equals(produtoEscolhido.getId())) {
				int quantidade = produtoEscolhido.getQuantidade() - 1;
				produtoEscolhido.setQuantidade(quantidade);
				int index = produtos.indexOf(produto);
				produtos.set(index, produtoEscolhido);
			}
		}
		return produtos;
	}

	public static void exibirComprasEfetuadas(List<Venda> vendas, Usuario usuarioLogado) {
		System.out.println();
		System.out.println("************************************************************");
		System.out.println("COMPRAS EFETUADAS");
		vendas.stream().forEach(venda -> {
			if (venda.getCliente().getUsername().equals(usuarioLogado.getUsername())) {
				System.out.println("************************************************************");
				System.out.println("Compra de código: " + venda.getCódigo() + " na empresa "
						+ venda.getEmpresa().getNome() + ": ");
				venda.getItens().stream().forEach(x -> {
					System.out.printf(localeBR, "%-4s - %-25s - R$ %.2f%n", x.getId(), x.getNome(), x.getPreco());
				});
				System.out.printf(localeBR, "Total: R$ %.2f%n", venda.getValor());
				System.out.println("************************************************************");
			}
		});
	}

	public static void listarMeusProdutos(List<Produto> produtos, Empresa empresa) {
		System.out.println();
		System.out.println("************************************************************");
		System.out.println("MEUS PRODUTOS");
		produtos.stream().forEach(produto -> {
			if (produto.getEmpresa().getId().equals(empresa.getId())) {
				System.out.println("************************************************************");
				System.out.println("Código: " + produto.getId());
				System.out.println("Produto: " + produto.getNome());
				System.out.println("Quantidade em estoque: " + produto.getQuantidade());
				System.out.printf(localeBR, "Valor: R$ %.2f%n", produto.getPreco());
				System.out.println("************************************************************");
			}
		});
		System.out.printf(localeBR, "Saldo Empresa: R$ %.2f%n", empresa.getSaldo());
		System.out.println("************************************************************");
	}

	public static void exibirVendasEfetuadas(List<Venda> vendas, Empresa empresa) {
		System.out.println();
		System.out.println("************************************************************");
		System.out.println("VENDAS EFETUADAS");
		vendas.stream().forEach(venda -> {
			if (venda.getEmpresa().getId().equals(empresa.getId())) {
				System.out.println("************************************************************");
				System.out.println(
						"Venda de código: " + venda.getCódigo() + " no CPF " + venda.getCliente().getCpf() + ": ");
				venda.getItens().stream().forEach(x -> {
					System.out.printf(localeBR, "%-4s - %-25s - R$ %.2f%n", x.getId(), x.getNome(), x.getPreco());
				});
				System.out.printf(localeBR, "Total Venda: R$ %.2f%n", venda.getValor());
				System.out.printf(localeBR, "Total Taxa a ser paga: R$ %.2f%n", venda.getComissaoSistema());
				System.out.printf(localeBR, "Total Líquido  para empresa R$ %.2f%n",
						(venda.getValor() - venda.getComissaoSistema()));
				System.out.println("************************************************************");
			}
		});
		System.out.printf(localeBR, "Saldo Empresa: R$ %.2f%n", empresa.getSaldo());
		System.out.println("************************************************************");
	}

	public static void exibirResumoCompra(List<Produto> carrinho, Integer codigoEmpresa, List<Empresa> empresas,
			List<Cliente> clientes, Usuario usuarioLogado, List<Venda> vendas) {
		System.out.println("************************************************************");
		System.out.println("Resumo da compra: ");
		carrinho.stream().forEach(x -> {
			if (x.getEmpresa().getId().equals(codigoEmpresa)) {
				System.out.printf(localeBR, "%-4s - %-25s - R$ %.2f%n", x.getId(), x.getNome(), x.getPreco());
			}
		});
		Empresa empresaEscolhida = empresas.stream().filter(x -> x.getId().equals(codigoEmpresa))
				.collect(Collectors.toList()).get(0);
		Cliente clienteLogado = clientes.stream().filter(x -> x.getUsername().equals(usuarioLogado.getUsername()))
				.collect(Collectors.toList()).get(0);
		Venda venda = criarVenda(carrinho, empresaEscolhida, clienteLogado, vendas);
		System.out.printf(localeBR, "Total: R$ %.2f%n", venda.getValor());
		System.out.println("************************************************************");
	}

}
