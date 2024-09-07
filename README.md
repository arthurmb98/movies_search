# Movies Search Application

## Descrição

Esta é uma aplicação Java que permite pesquisar termos específicos em arquivos de texto descompactados a partir de um arquivo ZIP. A aplicação descompacta o arquivo ZIP contendo arquivos de texto e realiza uma busca nos arquivos para encontrar ocorrências do termo fornecido.

## Observações

Apagar a pasta 'data' na raiz do projeto, caso exista, ela é criada quando o app extrai os arquivos de movies.zip. Quando ela existe, a função unzip não é mais chamada.
Foi optado em não gerar um 'jar' do projeto, para que em tempo de execução se possa ver a aplicação extraindo os dados do arquivo .zip.

## Requisitos

- Java 11 ou superior
- Maven (opcional, se for usar para compilação e execução. Obrigatório para executar tests)

## Estrutura do Projeto

- `src/main/java/org/luiza_labs/movies_search/`: Contém o código-fonte Java.
    - `MoviesSearchApp.java`: Classe principal da aplicação.
    - `FileSearchService.java`: Serviço que realiza a busca em arquivos.
    - `ZipUtils.java`: Utilitário para descompactar arquivos ZIP.
- `target/classes/`: Diretório de saída para arquivos compilados.

## Compilação

Para compilar o projeto, execute o seguinte comando no terminal:

```sh
    javac -d target/classes src/main/java/org/luiza_labs/movies_search/*.java
```

## Execução

Para executar o programa, use o comando:

```sh
    java -cp target/classes org.luiza_labs.movies_search.MoviesSearchApp "nome_filtro"
```

## Testes

Para executar os testes (se configurados), use o comando Maven:

```sh
    mvn test
```
Certifique-se de que o Maven está instalado e configurado no seu ambiente para executar este comando.

## Problemas Conhecidos

Problema com a configuração do PrintStream: Se você encontrar problemas relacionados ao encoding dos caracteres, verifique se o seu terminal ou IDE está configurado para usar UTF-8.

## Licença

Este projeto está licenciado sob a Licença ORACLE JAVA.
