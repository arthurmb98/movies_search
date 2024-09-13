@echo off
setlocal

set SRC_DIR=src/org/luiza_labs/movies_search
set OUT_DIR=target/classes

rem Cria o diretório de saída se não existir
if not exist %OUT_DIR% mkdir %OUT_DIR%

rem Compila os arquivos Java
for /r %SRC_DIR% %%f in (*.java) do javac -d %OUT_DIR% %%f

endlocal