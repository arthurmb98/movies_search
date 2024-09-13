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
      - `controllers/MoviesController.java`: Controlador responsável pela lógica de controle da aplicação. Gerencia a interação entre a aplicação e os dados, incluindo a carga de arquivos ZIP e a busca de filmes.
      - `views/MoviesView.java`: Visão responsável pela interface com o usuário. Exibe os resultados da busca e outras informações relevantes para o usuário.
      - `services/FileService.java`: Serviço que realiza a busca em arquivos. Implementa a lógica para procurar filmes em arquivos descompactados.
      - `utils/ZipUtils.java`: Utilitário para descompactar arquivos ZIP.
- `target/classes/`: Diretório de saída para arquivos compilados.

## Compilação

### Existem duas formas de compilar, via comando javac ou pelo Maven:

Para compilar e executar o projeto via prompt de comando, execute a seguinte linha:

```sh
    compile.bat
```
###### Foi criado um arquivo bat para setar os diretórios que devem ser compilados.

Pode-se também compilar e já executar os testes pelo Maven, basta executar o seguinte comando no terminal:

```sh
    mvn clean install
```
Certifique-se de que o Maven está instalado e configurado no seu ambiente para executar este comando.

## Execução

### Existem duas formas de executar a plicação:

Para executar o programa pelo prompt de o comando java:
###### Essa execução só funciona caso o projeto tenha sido compilado pelo comando 'compile.bat'
###### Lembre-se do termo a ser procurado no fim do comando.

```sh
    java -cp target/classes org.luiza_labs.movies_search.MoviesSearchApp "john"
```

Para executar o programa via Maven (Maneira mais fácil):

```sh
    mvn run
```

###### Após subir a aplicação pelo Maven, uma tela de pesquisa aparecerá para o usuário informar o termo. Após mostrar os resultados, o usuário pode procuraar por outro termo ou encerrar a aplicação.

## Testes

Para executar os testes, use o comando Maven:

```sh
    mvn test
```

## Problemas Conhecidos

Problema com a configuração do PrintStream: Se você encontrar problemas relacionados ao encoding dos caracteres, verifique se o seu terminal ou IDE está configurado para usar UTF-8.

## Licença

Este projeto está licenciado sob a Licença ORACLE JAVA.
