# Atividade 024 - Padrão Visitor

Refatoração do catálogo de produtos utilizando o padrão **Visitor**, incluindo o desafio adicional do valor total do catálogo.

## Estrutura

| Classe | Papel |
|---|---|
| `Product` | Elemento: abstração com `accept(ProductVisitor visitor)` |
| `Book`, `Electronics` | Elementos concretos: guardam os dados e chamam `visitor.visit(this)` |
| `ProductVisitor` | Visitante: `visit(Book)` e `visit(Electronics)` |
| `ProductReportVisitor` | Exibe tipo, título ou modelo e preço original em centavos |
| `PromotionalPriceVisitor` | Exibe o preço promocional: livros com 10% e eletrônicos com 5% de desconto |
| `TotalPriceVisitor` | Desafio adicional: soma os preços originais num acumulador `long` |
| `Main` | Cliente: percorre a coleção chamando apenas `product.accept(visitor)` |

O `ProductReport` com `instanceof` e conversões de tipo foi removido. O cálculo promocional usa `long` na multiplicação e descarta a fração de centavo só no final (999 → 899).

## Como executar

```bash
javac -d out src/*.java
java -cp out Main
```

## Questões para reflexão

**a) Qual problema existente no código inicial foi resolvido com o Visitor?**

O `ProductReport` usava `instanceof` e conversões para descobrir o tipo de cada produto. Cada nova operação, como o preço promocional, repetiria essas verificações em outro serviço. A alternativa seria colocar todas as operações dentro dos produtos, misturando responsabilidades. Com o Visitor, cada operação vira uma classe com um método por tipo de produto, e os produtos só precisam aceitar o visitante.

**b) Quais abstrações e classes representam os elementos, os elementos concretos, o visitante e os visitantes concretos na sua implementação?**

- Elementos: `Product`
- Elementos concretos: `Book` e `Electronics`
- Visitante: `ProductVisitor`
- Visitantes concretos: `ProductReportVisitor`, `PromotionalPriceVisitor` e `TotalPriceVisitor`

**c) Como a chamada `product.accept(visitor)` seguida de `visitor.visit(this)` permite selecionar o comportamento adequado? Por que apenas sobrecarregar `visit()` e chamar `visitor.visit(product)` no cliente não produz o mesmo resultado?**

Isso é o "duplo despacho". Na primeira chamada, o Java escolhe o `accept` pelo tipo real do objeto (`Book` ou `Electronics`). Dentro dele, `this` já tem o tipo concreto, então o compilador escolhe a sobrecarga certa de `visit`. Já no cliente, a variável é do tipo `Product`. Como a sobrecarga é escolhida em tempo de compilação pelo tipo declarado, e não existe `visit(Product)`, o código nem compilaria. E se existisse, sempre chamaria essa versão genérica.

**d) Quais mudanças seriam necessárias para incluir um novo tipo `Furniture`? Como esse custo difere do custo de acrescentar uma nova operação por meio de outro visitante?**

Seria preciso criar a classe `Furniture` com `accept`, adicionar `visit(Furniture)` em `ProductVisitor` e implementar esse método em todos os visitantes existentes. Ou seja, uma mudança espalhada. Já uma nova operação é barata: basta criar um novo visitante, como foi feito com o `TotalPriceVisitor`, sem tocar nos produtos nem nos outros visitantes. Por isso o Visitor é indicado quando os tipos são estáveis e as operações mudam com frequência.

**e) Por que reutilizar um visitante com acumulador pode alterar o resultado de um novo percurso? Que cuidados são necessários para que os visitantes acessem os dados sem expor desnecessariamente o estado interno dos elementos?**

O `TotalPriceVisitor` guarda a soma num atributo. Se a mesma instância percorrer o catálogo de novo, ela continua somando a partir do valor anterior e o total sai dobrado. Por isso cada cálculo usa uma instância nova, ou o visitante precisaria de um método para zerar. Para não expor demais os elementos, eles devem oferecer só os métodos de consulta necessários (como `getTitle()` e `getPriceInCents()`), sem setters nem acesso a coleções internas que permitam ao visitante modificar o produto.
