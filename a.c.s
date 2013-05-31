.data
.globl str_0
	str_0: .asciiz " O"
.globl str_1
	str_1: .asciiz " ."
.globl str_2
	str_2: .asciiz "\n"
.align 2
.globl data_gp
data_gp:
.globl data_ID1
	data_ID1: .space 4
.globl data_ID2
	data_ID2: .space 32
.globl data_ID3
	data_ID3: .space 32
.globl data_ID4
	data_ID4: .space 120
.text
.align 2
.globl main
main:
	la $gp, data_gp
	sw $0, 0($sp)
	sw $0, -4($sp)
	addi $sp, $sp, -8 # move up a cell
	move $fp, $sp     # start using memory here
	li $t1, 8
	li $t2, 0
	add $t0, $t1, $t2
	la $gp, data_ID1
	sw $t0, 0($gp)
	jal Label_main
end_main:
	li $v0, 10        # exit
	syscall
Label_printBoard:
	sw $31, 4($fp)
	addi $sp, $sp, -56
	li $t1, 0
	li $t2, 0
	add $t0, $t1, $t2
	sw $t0, 0($fp)
Label_1:
	lw $t1, 0($fp)
	la $gp, data_ID1
	lw $t2, 0($gp)
	slt $t0, $t1, $t2
	sw $t0, -8($fp)
	lw $t0, -8($fp)
	beqz $t0, Label_2
	li $t1, 0
	li $t2, 0
	add $t0, $t1, $t2
	sw $t0, -4($fp)
Label_4:
	lw $t1, -4($fp)
	la $gp, data_ID1
	lw $t2, 0($gp)
	slt $t0, $t1, $t2
	sw $t0, -12($fp)
	lw $t0, -12($fp)
	beqz $t0, Label_5
	li $t1, 0
	li $t2, 0
	add $t0, $t1, $t2
	sw $t0, -16($fp)
	lw $t1, -16($fp)
	lw $t2, 0($fp)
	add $t0, $t1, $t2
	sw $t0, -16($fp)
	lw $t1, -16($fp)
	li $t2, 4
	mul $t0, $t1, $t2
	sw $t0, -16($fp)
	lw $t3, -16($fp)
	la $gp, data_ID3
	add $t3, $gp, $t3
	lw $t1, 0($t3)
	lw $t2, -4($fp)
	seq $t0, $t1, $t2
	sw $t0, -20($fp)
	lw $t0, -20($fp)
	beqz $t0, Label_7
	addi $sp, $sp, -4
	sub $t0, $fp, $sp
	sw $t0, 0($sp)
	addi $sp, $sp, -8
	la $a0, str_0
	sw $a0, -0($sp)
	add $fp, $sp, $0
	jal Label_printf
	sw $v0, -24($fp)
Label_8:
Label_7:
	addi $sp, $sp, -4
	sub $t0, $fp, $sp
	sw $t0, 0($sp)
	addi $sp, $sp, -8
	la $a0, str_1
	sw $a0, -0($sp)
	add $fp, $sp, $0
	jal Label_printf
	sw $v0, -28($fp)
Label_9:
Label_6:
	li $t1, 0
	li $t2, 0
	add $t0, $t1, $t2
	sw $t0, -32($fp)
	lw $t3, -32($fp)
	sub $t3, $fp, $t3
	lw $t1, -4($t3)
	li $t2, 1
	add $t0, $t1, $t2
	lw $t3, -32($fp)
	sub $t3, $fp, $t3
	sw $t0, -4($t3)
	lw $t3, -32($fp)
	sub $t3, $fp, $t3
	lw $t1, -4($t3)
	li $t2, 1
	add $t0, $t1, $t2
	sw $t0, -36($fp)
	j Label_4
Label_5:
	addi $sp, $sp, -4
	sub $t0, $fp, $sp
	sw $t0, 0($sp)
	addi $sp, $sp, -8
	la $a0, str_2
	sw $a0, -0($sp)
	add $fp, $sp, $0
	jal Label_printf
	sw $v0, -40($fp)
Label_10:
Label_3:
	li $t1, 0
	li $t2, 0
	add $t0, $t1, $t2
	sw $t0, -44($fp)
	lw $t3, -44($fp)
	sub $t3, $fp, $t3
	lw $t1, 0($t3)
	li $t2, 1
	add $t0, $t1, $t2
	lw $t3, -44($fp)
	sub $t3, $fp, $t3
	sw $t0, 0($t3)
	lw $t3, -44($fp)
	sub $t3, $fp, $t3
	lw $t1, 0($t3)
	li $t2, 1
	add $t0, $t1, $t2
	sw $t0, -48($fp)
	j Label_1
Label_2:
	addi $sp, $sp, -4
	sub $t0, $fp, $sp
	sw $t0, 0($sp)
	addi $sp, $sp, -8
	la $a0, str_2
	sw $a0, -0($sp)
	add $fp, $sp, $0
	jal Label_printf
	sw $v0, -52($fp)
Label_11:
	addi $sp, $fp, 0
	lw $t0, 8($sp)
	lw $31, 4($sp)
	addi $sp, $sp, 8
	add $fp, $sp, $t0
	jr $31
Label_search:
	sw $31, 4($fp)
	addi $sp, $sp, -112
	lw $t1, 0($fp)
	la $gp, data_ID1
	lw $t2, 0($gp)
	seq $t0, $t1, $t2
	sw $t0, -4($fp)
	lw $t0, -4($fp)
	beqz $t0, Label_12
	addi $sp, $sp, -4
	sub $t0, $fp, $sp
	sw $t0, 0($sp)
	addi $sp, $sp, -8
	add $fp, $sp, $0
	jal Label_printBoard
	sw $v0, -8($fp)
Label_13:
Label_12:
	li $t1, 0
	li $t2, 0
	add $t0, $t1, $t2
	sw $t0, -12($fp)
Label_14:
	lw $t1, -12($fp)
	la $gp, data_ID1
	lw $t2, 0($gp)
	slt $t0, $t1, $t2
	sw $t0, -16($fp)
	lw $t0, -16($fp)
	beqz $t0, Label_15
	li $t1, 0
	li $t2, 0
	add $t0, $t1, $t2
	sw $t0, -20($fp)
	lw $t1, -20($fp)
	lw $t2, -12($fp)
	add $t0, $t1, $t2
	sw $t0, -20($fp)
	lw $t1, -20($fp)
	li $t2, 4
	mul $t0, $t1, $t2
	sw $t0, -20($fp)
	lw $t3, -20($fp)
	la $gp, data_ID2
	add $t3, $gp, $t3
	lw $t1, 0($t3)
	li $t2, 0
	seq $t0, $t1, $t2
	sw $t0, -24($fp)
	li $t1, 0
	li $t2, 0
	add $t0, $t1, $t2
	sw $t0, -28($fp)
	lw $t1, -28($fp)
	li $t2, 0
	add $t0, $t1, $t2
	sw $t0, -28($fp)
	lw $t1, -28($fp)
	li $t2, 2
	mul $t0, $t1, $t2
	sw $t0, -28($fp)
	lw $t1, -12($fp)
	lw $t2, 0($fp)
	add $t0, $t1, $t2
	sw $t0, -32($fp)
	lw $t1, -28($fp)
	lw $t2, -32($fp)
	add $t0, $t1, $t2
	sw $t0, -28($fp)
	lw $t1, -28($fp)
	li $t2, 4
	mul $t0, $t1, $t2
	sw $t0, -28($fp)
	lw $t3, -28($fp)
	la $gp, data_ID4
	add $t3, $gp, $t3
	lw $t1, 0($t3)
	li $t2, 0
	seq $t0, $t1, $t2
	sw $t0, -36($fp)
	lw $t1, -24($fp)
	lw $t2, -36($fp)
	and $t0, $t1, $t2
	sw $t0, -24($fp)
	li $t1, 0
	li $t2, 0
	add $t0, $t1, $t2
	sw $t0, -40($fp)
	lw $t1, -40($fp)
	li $t2, 1
	add $t0, $t1, $t2
	sw $t0, -40($fp)
	lw $t1, -40($fp)
	li $t2, 2
	mul $t0, $t1, $t2
	sw $t0, -40($fp)
	lw $t1, -12($fp)
	la $gp, data_ID1
	lw $t2, 0($gp)
	add $t0, $t1, $t2
	sw $t0, -44($fp)
	lw $t1, -44($fp)
	li $t2, 1
	sub $t0, $t1, $t2
	sw $t0, -44($fp)
	lw $t1, -44($fp)
	lw $t2, 0($fp)
	sub $t0, $t1, $t2
	sw $t0, -44($fp)
	lw $t1, -40($fp)
	lw $t2, -44($fp)
	add $t0, $t1, $t2
	sw $t0, -40($fp)
	lw $t1, -40($fp)
	li $t2, 4
	mul $t0, $t1, $t2
	sw $t0, -40($fp)
	lw $t3, -40($fp)
	la $gp, data_ID4
	add $t3, $gp, $t3
	lw $t1, 0($t3)
	li $t2, 0
	seq $t0, $t1, $t2
	sw $t0, -48($fp)
	lw $t1, -24($fp)
	lw $t2, -48($fp)
	and $t0, $t1, $t2
	sw $t0, -24($fp)
	lw $t0, -24($fp)
	beqz $t0, Label_17
	li $t1, 0
	li $t2, 0
	add $t0, $t1, $t2
	sw $t0, -52($fp)
	lw $t1, -52($fp)
	li $t2, 1
	add $t0, $t1, $t2
	sw $t0, -52($fp)
	lw $t1, -52($fp)
	li $t2, 2
	mul $t0, $t1, $t2
	sw $t0, -52($fp)
	lw $t1, -12($fp)
	la $gp, data_ID1
	lw $t2, 0($gp)
	add $t0, $t1, $t2
	sw $t0, -56($fp)
	lw $t1, -56($fp)
	li $t2, 1
	sub $t0, $t1, $t2
	sw $t0, -56($fp)
	lw $t1, -56($fp)
	lw $t2, 0($fp)
	sub $t0, $t1, $t2
	sw $t0, -56($fp)
	lw $t1, -52($fp)
	lw $t2, -56($fp)
	add $t0, $t1, $t2
	sw $t0, -52($fp)
	lw $t1, -52($fp)
	li $t2, 4
	mul $t0, $t1, $t2
	sw $t0, -52($fp)
	li $t1, 1
	li $t2, 0
	add $t0, $t1, $t2
	lw $t3, -52($fp)
	la $gp, data_ID4
	add $t3, $gp, $t3
	sw $t0, 0($t3)
	li $t1, 0
	li $t2, 0
	add $t0, $t1, $t2
	sw $t0, -60($fp)
	lw $t1, -60($fp)
	li $t2, 0
	add $t0, $t1, $t2
	sw $t0, -60($fp)
	lw $t1, -60($fp)
	li $t2, 2
	mul $t0, $t1, $t2
	sw $t0, -60($fp)
	lw $t1, -12($fp)
	lw $t2, 0($fp)
	add $t0, $t1, $t2
	sw $t0, -64($fp)
	lw $t1, -60($fp)
	lw $t2, -64($fp)
	add $t0, $t1, $t2
	sw $t0, -60($fp)
	lw $t1, -60($fp)
	li $t2, 4
	mul $t0, $t1, $t2
	sw $t0, -60($fp)
	lw $t3, -52($fp)
	la $gp, data_ID4
	add $t3, $gp, $t3
	lw $t1, 0($t3)
	li $t2, 0
	add $t0, $t1, $t2
	lw $t3, -60($fp)
	la $gp, data_ID4
	add $t3, $gp, $t3
	sw $t0, 0($t3)
	li $t1, 0
	li $t2, 0
	add $t0, $t1, $t2
	sw $t0, -68($fp)
	lw $t1, -68($fp)
	lw $t2, -12($fp)
	add $t0, $t1, $t2
	sw $t0, -68($fp)
	lw $t1, -68($fp)
	li $t2, 4
	mul $t0, $t1, $t2
	sw $t0, -68($fp)
	lw $t3, -60($fp)
	la $gp, data_ID4
	add $t3, $gp, $t3
	lw $t1, 0($t3)
	li $t2, 0
	add $t0, $t1, $t2
	lw $t3, -68($fp)
	la $gp, data_ID2
	add $t3, $gp, $t3
	sw $t0, 0($t3)
	li $t1, 0
	li $t2, 0
	add $t0, $t1, $t2
	sw $t0, -72($fp)
	lw $t1, -72($fp)
	lw $t2, 0($fp)
	add $t0, $t1, $t2
	sw $t0, -72($fp)
	lw $t1, -72($fp)
	li $t2, 4
	mul $t0, $t1, $t2
	sw $t0, -72($fp)
	lw $t1, -12($fp)
	li $t2, 0
	add $t0, $t1, $t2
	lw $t3, -72($fp)
	la $gp, data_ID3
	add $t3, $gp, $t3
	sw $t0, 0($t3)
	lw $t1, 0($fp)
	li $t2, 1
	add $t0, $t1, $t2
	sw $t0, -76($fp)
	addi $sp, $sp, -4
	sub $t0, $fp, $sp
	sw $t0, 0($sp)
	addi $sp, $sp, -8
	lw $a0, -76($fp)
	sw $a0, -0($sp)
	add $fp, $sp, $0
	jal Label_search
	sw $v0, -80($fp)
Label_18:
	li $t1, 0
	li $t2, 0
	add $t0, $t1, $t2
	sw $t0, -84($fp)
	lw $t1, -84($fp)
	li $t2, 1
	add $t0, $t1, $t2
	sw $t0, -84($fp)
	lw $t1, -84($fp)
	li $t2, 2
	mul $t0, $t1, $t2
	sw $t0, -84($fp)
	lw $t1, -12($fp)
	la $gp, data_ID1
	lw $t2, 0($gp)
	add $t0, $t1, $t2
	sw $t0, -88($fp)
	lw $t1, -88($fp)
	li $t2, 1
	sub $t0, $t1, $t2
	sw $t0, -88($fp)
	lw $t1, -88($fp)
	lw $t2, 0($fp)
	sub $t0, $t1, $t2
	sw $t0, -88($fp)
	lw $t1, -84($fp)
	lw $t2, -88($fp)
	add $t0, $t1, $t2
	sw $t0, -84($fp)
	lw $t1, -84($fp)
	li $t2, 4
	mul $t0, $t1, $t2
	sw $t0, -84($fp)
	li $t1, 0
	li $t2, 0
	add $t0, $t1, $t2
	lw $t3, -84($fp)
	la $gp, data_ID4
	add $t3, $gp, $t3
	sw $t0, 0($t3)
	li $t1, 0
	li $t2, 0
	add $t0, $t1, $t2
	sw $t0, -92($fp)
	lw $t1, -92($fp)
	li $t2, 0
	add $t0, $t1, $t2
	sw $t0, -92($fp)
	lw $t1, -92($fp)
	li $t2, 2
	mul $t0, $t1, $t2
	sw $t0, -92($fp)
	lw $t1, -12($fp)
	lw $t2, 0($fp)
	add $t0, $t1, $t2
	sw $t0, -96($fp)
	lw $t1, -92($fp)
	lw $t2, -96($fp)
	add $t0, $t1, $t2
	sw $t0, -92($fp)
	lw $t1, -92($fp)
	li $t2, 4
	mul $t0, $t1, $t2
	sw $t0, -92($fp)
	lw $t3, -84($fp)
	la $gp, data_ID4
	add $t3, $gp, $t3
	lw $t1, 0($t3)
	li $t2, 0
	add $t0, $t1, $t2
	lw $t3, -92($fp)
	la $gp, data_ID4
	add $t3, $gp, $t3
	sw $t0, 0($t3)
	li $t1, 0
	li $t2, 0
	add $t0, $t1, $t2
	sw $t0, -100($fp)
	lw $t1, -100($fp)
	lw $t2, -12($fp)
	add $t0, $t1, $t2
	sw $t0, -100($fp)
	lw $t1, -100($fp)
	li $t2, 4
	mul $t0, $t1, $t2
	sw $t0, -100($fp)
	lw $t3, -92($fp)
	la $gp, data_ID4
	add $t3, $gp, $t3
	lw $t1, 0($t3)
	li $t2, 0
	add $t0, $t1, $t2
	lw $t3, -100($fp)
	la $gp, data_ID2
	add $t3, $gp, $t3
	sw $t0, 0($t3)
Label_17:
Label_16:
	li $t1, 0
	li $t2, 0
	add $t0, $t1, $t2
	sw $t0, -104($fp)
	lw $t3, -104($fp)
	sub $t3, $fp, $t3
	lw $t1, -12($t3)
	li $t2, 1
	add $t0, $t1, $t2
	lw $t3, -104($fp)
	sub $t3, $fp, $t3
	sw $t0, -12($t3)
	lw $t3, -104($fp)
	sub $t3, $fp, $t3
	lw $t1, -12($t3)
	li $t2, 1
	add $t0, $t1, $t2
	sw $t0, -108($fp)
	j Label_14
Label_15:
	addi $sp, $fp, 0
	lw $t0, 8($sp)
	lw $31, 4($sp)
	addi $sp, $sp, 8
	add $fp, $sp, $t0
	jr $31
Label_main:
	sw $31, 4($fp)
	addi $sp, $sp, -4
	addi $sp, $sp, -4
	sub $t0, $fp, $sp
	sw $t0, 0($sp)
	addi $sp, $sp, -8
	li $a0, 0
	sw $a0, -0($sp)
	add $fp, $sp, $0
	jal Label_search
	sw $v0, 0($fp)
Label_19:
	li $v0, 0
	addi $sp, $fp, 0
	lw $t0, 8($sp)
	lw $31, 4($sp)
	addi $sp, $sp, 8
	add $fp, $sp, $t0
	jr $31
	addi $sp, $fp, 0
	lw $t0, 8($sp)
	lw $31, 4($sp)
	addi $sp, $sp, 8
	add $fp, $sp, $t0
	jr $31

########################################
############### CODE GEN ###############
########################################
Label_printf:
	sw $31, 4($fp)
	addi $sp, $sp, -4
	move $v1, $a0
	addi $a1, $fp, -4
	la $s6, printf_buf 			# set s6 = base of printf buffer.

printf_loop:
	lb $a0, 0($v1)
	beq $a0, '%', printf_fmt 	# if the fmt character, then do fmt.
	beq $0, $a0, printf_end 	# if zero, then go to end.
	sb $a0, 0($s6)
	move $a0, $s6
	li $v0, 4
	syscall
printf_next:
	addi $v1, $v1, 1
	b printf_loop

printf_fmt:
	addi $v1, $v1, 1
	lb $a0, 0($v1)
	beq $a0, 'd', printf_int 	# if 'd', print as a decimal integer.
	beq $a0, 's', printf_str 	# if 's', print as a string.
	beq $a0, 'c', printf_char 	# if 'c', print as a ASCII char.
	beq $a0, '%', printf_perc 	# if '%', print a '%'
printf_int:
	lw $a0, 0($a1)
	addi $a1, $a1, -4
	li $v0, 1
	syscall
	b printf_next
printf_char:
	lw $a0, 0($a1)
	addi $a1, $a1, -4
	sb $a0, 0($s6)
	move $a0, $s6
	li $v0, 4
	syscall
	b printf_next
printf_str:
	lw $a0, 0($a1)
	addi $a1, $a1, -4
	li $v0, 4
	syscall
	b printf_next
printf_perc:
	la $a0, '%'
	sb $a0, 0($s6)
	move $a0, $s6
	li $v0, 4
	syscall
	b printf_next

printf_end:						#roll back
	addi $sp, $fp, 0
	lw $t0, 8($sp)
	lw $31, 4($sp)
	addi $sp, $sp, 8
	add $fp, $sp, $t0
	jr $31

.data
printf_buf: .space 2
	
