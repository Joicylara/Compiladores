; +-------------------------------------------------+
; | Assembly
;                                    |
; | Data : 29/05/2023 13:30:07                      |
; +-------------------------------------------------+

section .data                                                                   ; Inicio da seção de constantes
    fmtin: db "%d", 0x0                                                         ; Formatador de input
    fmtout: db "%d", 0xA, 0x0                                                   ; Formatador de output

    str_0: db "digite um numero", 0xA, 0x0                                      ; Declaração da string
    str_1: db "digite um numero", 0xA, 0x0                                      ; Declaração da string
    str_2: db "digite um numero", 0xA, 0x0                                      ; Declaração da string
    str_3: db " nao e triangulo", 0xA, 0x0                                      ; Declaração da string
    str_4: db "e triangulo", 0xA, 0x0                                           ; Declaração da string
    str_5: db "escaleno", 0xA, 0x0                                              ; Declaração da string
    str_6: db "isolceles", 0xA, 0x0                                             ; Declaração da string
    str_7: db "equilatero", 0xA, 0x0                                            ; Declaração da string

section .bss                                                                    ; Inicio da seção de variáveis
    a: resd 1                                                                   ; Declaração da variável a
    b: resd 1                                                                   ; Declaração da variável b
    c: resd 1                                                                   ; Declaração da variável c
    d: resd 1                                                                   ; Declaração da variável d

section .text                                                                   ; Inicio da seção do código
    global main                                                                 ; Declaração do main
    extern printf                                                               ; Importação do printf
    extern scanf                                                                ; Importação do scanf

main:
    ; Preparação da pilha
    push ebp
    mov ebp, esp

    ; Escrever a string str_0 na saída
    push dword str_0
    call printf
    add esp, 4

    ; Ler a entrada do usuário para a variável a
    push a
    push dword fmtin
    call scanf
    add esp, 8

    ; Escrever a string str_1 na saída
    push dword str_1
    call printf
    add esp, 4

    ; Ler a entrada do usuário para a variável b
    push b
    push dword fmtin
    call scanf
    add esp, 8

    ; Escrever a string str_2 na saída
    push dword str_2
    call printf
    add esp, 4

    ; Ler a entrada do usuário para a variável c
    push c
    push dword fmtin
    call scanf
    add esp, 8

mov eax, [a]
cmp eax, c
jae _L1

    mov eax, [d]
    mov ebx, 1
    sub eax, ebx
    mov [d], eax
_L1:
mov eax, [c]
cmp eax, a
jae _L2

    mov eax, [d]
    mov ebx, 1
    sub eax, ebx
    mov [d], eax
_L2:
mov eax, [b]
cmp eax, c
jae _L3

    mov eax, [d]
    mov ebx, 1
    sub eax, ebx
    mov [d], eax
_L3:
mov eax, 0
cmp eax, d
jae _L4

    ; Escrever a string str_3 na saída
    push dword str_3
    call printf
    add esp, 4

    jmp _L5
_L4:
    ; Escrever a string str_4 na saída
    push dword str_4
    call printf
    add esp, 4

mov eax, [b]
cmp eax, c
jne _L6

    mov eax, [d]
    mov ebx, 1
    add eax, ebx
    mov [d], eax
_L6:
mov eax, [a]
cmp eax, c
jne _L7

    mov eax, [d]
    mov ebx, 1
    add eax, ebx
    mov [d], eax
_L7:
mov eax, [b]
cmp eax, a
jne _L8

    mov eax, [d]
    mov ebx, 1
    add eax, ebx
    mov [d], eax
_L8:
mov eax, [d]
cmp eax, 1
jae _L9

    ; Escrever a string str_5 na saída
    push dword str_5
    call printf
    add esp, 4

    jmp _L10
_L9:
mov eax, [d]
cmp eax, 1
jne _L11

    ; Escrever a string str_6 na saída
    push dword str_6
    call printf
    add esp, 4

    jmp _L12
_L11:
mov eax, 1
cmp eax, d
jae _L13

    ; Escrever a string str_7 na saída
    push dword str_7
    call printf
    add esp, 4

_L13:
_L12:
_L10:
_L5:
    ; Término do programa
    mov esp, ebp
    pop ebp
    ret
