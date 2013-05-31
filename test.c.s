.data
.globl str_0
	str_0: .asciiz "%d"
.globl str_1
	str_1: .asciiz "\n"
.align 2
.globl data_gp
data_gp:
.text
.align 2
.globl main
main:
	la $gp, data_gp
	sw $0, 0($sp)
	sw $0, -4($sp)
	addi $sp, $sp, -8 # move up a cell
	move $fp, $sp     # start using memory here
	jal Label_main
end_main:
	li $v0, 10        # exit
	syscall
Label_plus:
	sw $31, 4($fp)
	addi $sp, $sp, -80
	li $t1, 0
	li $t2, 0
	add $t0, $t1, $t2
	sw $t0, -16($fp)
	li $t1, 0
	li $t2, 0
	add $t0, $t1, $t2
	sw $t0, -20($fp)
Label_1:
	lw $t1, -20($fp)
	lw $t2, 0($fp)
	slt $t0, $t1, $t2
	sw $t0, -24($fp)
	lw $t0, -24($fp)
	beqz $t0, Label_2
	li $t1, 0
	li $t2, 0
	add $t0, $t1, $t2
	sw $t0, -28($fp)
	li $t1, 0
	li $t2, 0
	add $t0, $t1, $t2
	sw $t0, -32($fp)
	lw $t3, -28($fp)
	sub $t3, $fp, $t3
	lw $t1, -4($t3)
	lw $t3, -32($fp)
	sub $t3, $fp, $t3
	lw $t2, -8($t3)
	add $t0, $t1, $t2
	sw $t0, -36($fp)
	lw $t1, -36($fp)
	lw $t2, -16($fp)
	add $t0, $t1, $t2
	sw $t0, -36($fp)
	li $t1, 0
	li $t2, 0
	add $t0, $t1, $t2
	sw $t0, -40($fp)
	lw $t1, -36($fp)
	li $t2, 0
	add $t0, $t1, $t2
	lw $t3, -40($fp)
	sub $t3, $fp, $t3
	sw $t0, -12($t3)
	li $t1, 0
	li $t2, 0
	add $t0, $t1, $t2
	sw $t0, -16($fp)
	li $t1, 0
	li $t2, 0
	add $t0, $t1, $t2
	sw $t0, -44($fp)
	lw $t3, -44($fp)
	sub $t3, $fp, $t3
	lw $t1, -12($t3)
	li $t2, 9
	sgt $t0, $t1, $t2
	sw $t0, -48($fp)
	lw $t0, -48($fp)
	beqz $t0, Label_3
	li $t1, 0
	li $t2, 0
	add $t0, $t1, $t2
	sw $t0, -52($fp)
	lw $t3, -52($fp)
	sub $t3, $fp, $t3
	lw $t1, -12($t3)
	li $t2, 10
	sub $t0, $t1, $t2
	sw $t0, -56($fp)
	li $t1, 0
	li $t2, 0
	add $t0, $t1, $t2
	sw $t0, -60($fp)
	lw $t1, -56($fp)
	li $t2, 0
	add $t0, $t1, $t2
	lw $t3, -60($fp)
	sub $t3, $fp, $t3
	sw $t0, -12($t3)
	li $t1, 1
	li $t2, 0
	add $t0, $t1, $t2
	sw $t0, -16($fp)
	j Label_4
Label_3:
Label_4:
	lw $t1, -20($fp)
	li $t2, 1
	add $t0, $t1, $t2
	sw $t0, -64($fp)
	lw $t1, -64($fp)
	li $t2, 0
	add $t0, $t1, $t2
	sw $t0, -20($fp)
	j Label_1
Label_2:
	lw $t1, -16($fp)
	li $t2, 0
	sgt $t0, $t1, $t2
	sw $t0, -68($fp)
	lw $t0, -68($fp)
	beqz $t0, Label_5
	li $t1, 0
	li $t2, 0
	add $t0, $t1, $t2
	sw $t0, -72($fp)
	li $t1, 1
	li $t2, 0
	add $t0, $t1, $t2
	lw $t3, -72($fp)
	sub $t3, $fp, $t3
	sw $t0, -12($t3)
	lw $v0, -20($fp)
	addi $sp, $fp, 0
	lw $t0, 8($sp)
	lw $31, 4($sp)
	addi $sp, $sp, 8
	add $fp, $sp, $t0
	jr $31
	j Label_6
Label_5:
	lw $t1, -20($fp)
	li $t2, 1
	sub $t0, $t1, $t2
	sw $t0, -76($fp)
	lw $v0, -76($fp)
	addi $sp, $fp, 0
	lw $t0, 8($sp)
	lw $31, 4($sp)
	addi $sp, $sp, 8
	add $fp, $sp, $t0
	jr $31
Label_6:
	addi $sp, $fp, 0
	lw $t0, 8($sp)
	lw $31, 4($sp)
	addi $sp, $sp, 8
	add $fp, $sp, $t0
	jr $31
Label_printIntA:
	sw $31, 4($fp)
	addi $sp, $sp, -28
Label_7:
	lw $t1, 0($fp)
	li $t2, 0
	sge $t0, $t1, $t2
	sw $t0, -8($fp)
	lw $t0, -8($fp)
	beqz $t0, Label_8
	li $t1, 0
	li $t2, 0
	add $t0, $t1, $t2
	sw $t0, -12($fp)
	addi $sp, $sp, -4
	sub $t0, $fp, $sp
	sw $t0, 0($sp)
	addi $sp, $sp, -8
	la $a0, str_0
	sw $a0, -0($sp)
	lw $t3, -12($fp)
	sub $t3, $fp, $t3
	lw $a1, -4($t3)
	sw $a1, -4($sp)
	add $fp, $sp, $0
	jal Label_printf
	sw $v0, -16($fp)
Label_9:
	lw $t1, 0($fp)
	li $t2, 1
	sub $t0, $t1, $t2
	sw $t0, -20($fp)
	lw $t1, -20($fp)
	li $t2, 0
	add $t0, $t1, $t2
	sw $t0, 0($fp)
	j Label_7
Label_8:
	addi $sp, $sp, -4
	sub $t0, $fp, $sp
	sw $t0, 0($sp)
	addi $sp, $sp, -8
	la $a0, str_1
	sw $a0, -0($sp)
	add $fp, $sp, $0
	jal Label_printf
	sw $v0, -24($fp)
Label_10:
	addi $sp, $fp, 0
	lw $t0, 8($sp)
	lw $31, 4($sp)
	addi $sp, $sp, 8
	add $fp, $sp, $t0
	jr $31
Label_printIntB:
	sw $31, 4($fp)
	addi $sp, $sp, -28
Label_11:
	lw $t1, 0($fp)
	li $t2, 0
	sge $t0, $t1, $t2
	sw $t0, -8($fp)
	lw $t0, -8($fp)
	beqz $t0, Label_12
	li $t1, 0
	li $t2, 0
	add $t0, $t1, $t2
	sw $t0, -12($fp)
	addi $sp, $sp, -4
	sub $t0, $fp, $sp
	sw $t0, 0($sp)
	addi $sp, $sp, -8
	la $a0, str_0
	sw $a0, -0($sp)
	lw $t3, -12($fp)
	sub $t3, $fp, $t3
	lw $a1, -4($t3)
	sw $a1, -4($sp)
	add $fp, $sp, $0
	jal Label_printf
	sw $v0, -16($fp)
Label_13:
	lw $t1, 0($fp)
	li $t2, 1
	sub $t0, $t1, $t2
	sw $t0, -20($fp)
	lw $t1, -20($fp)
	li $t2, 0
	add $t0, $t1, $t2
	sw $t0, 0($fp)
	j Label_11
Label_12:
	addi $sp, $sp, -4
	sub $t0, $fp, $sp
	sw $t0, 0($sp)
	addi $sp, $sp, -8
	la $a0, str_1
	sw $a0, -0($sp)
	add $fp, $sp, $0
	jal Label_printf
	sw $v0, -24($fp)
Label_14:
	addi $sp, $fp, 0
	lw $t0, 8($sp)
	lw $31, 4($sp)
	addi $sp, $sp, 8
	add $fp, $sp, $t0
	jr $31
Label_printBigInt:
	sw $31, 4($fp)
	addi $sp, $sp, -28
Label_15:
	lw $t1, 0($fp)
	li $t2, 0
	sge $t0, $t1, $t2
	sw $t0, -8($fp)
	lw $t0, -8($fp)
	beqz $t0, Label_16
	li $t1, 0
	li $t2, 0
	add $t0, $t1, $t2
	sw $t0, -12($fp)
	addi $sp, $sp, -4
	sub $t0, $fp, $sp
	sw $t0, 0($sp)
	addi $sp, $sp, -8
	la $a0, str_0
	sw $a0, -0($sp)
	lw $t3, -12($fp)
	sub $t3, $fp, $t3
	lw $a1, -4($t3)
	sw $a1, -4($sp)
	add $fp, $sp, $0
	jal Label_printf
	sw $v0, -16($fp)
Label_17:
	lw $t1, 0($fp)
	li $t2, 1
	sub $t0, $t1, $t2
	sw $t0, -20($fp)
	lw $t1, -20($fp)
	li $t2, 0
	add $t0, $t1, $t2
	sw $t0, 0($fp)
	j Label_15
Label_16:
	addi $sp, $sp, -4
	sub $t0, $fp, $sp
	sw $t0, 0($sp)
	addi $sp, $sp, -8
	la $a0, str_1
	sw $a0, -0($sp)
	add $fp, $sp, $0
	jal Label_printf
	sw $v0, -24($fp)
Label_18:
	addi $sp, $fp, 0
	lw $t0, 8($sp)
	lw $31, 4($sp)
	addi $sp, $sp, 8
	add $fp, $sp, $t0
	jr $31
Label_main:
	sw $31, 4($fp)
	addi $sp, $sp, -164
	li $t1, 15
	li $t2, 0
	add $t0, $t1, $t2
	sw $t0, 0($fp)
	lw $t1, 0($fp)
	li $t2, 4
	mul $t0, $t1, $t2
	sw $t0, -24($fp)
	addi $sp, $sp, -4
	sub $t0, $fp, $sp
	sw $t0, 0($sp)
	addi $sp, $sp, -8
	lw $a0, -24($fp)
	sw $a0, -0($sp)
	add $fp, $sp, $0
	jal Label_malloc
	sw $v0, -28($fp)
Label_19:
	lw $t1, -28($fp)
	li $t2, 0
	add $t0, $t1, $t2
	sw $t0, -4($fp)
	li $t1, 0
	li $t2, 0
	add $t0, $t1, $t2
	sw $t0, -20($fp)
Label_20:
	lw $t1, -20($fp)
	lw $t2, 0($fp)
	slt $t0, $t1, $t2
	sw $t0, -32($fp)
	lw $t0, -32($fp)
	beqz $t0, Label_21
	li $t1, 0
	li $t2, 0
	add $t0, $t1, $t2
	sw $t0, -36($fp)
	li $t1, 0
	li $t2, 0
	add $t0, $t1, $t2
	lw $t3, -36($fp)
	sub $t3, $fp, $t3
	sw $t0, -4($t3)
Label_22:
	lw $t1, -20($fp)
	li $t2, 1
	add $t0, $t1, $t2
	sw $t0, -40($fp)
	lw $t1, -40($fp)
	li $t2, 0
	add $t0, $t1, $t2
	sw $t0, -20($fp)
	j Label_20
Label_21:
	lw $t1, 0($fp)
	li $t2, 4
	mul $t0, $t1, $t2
	sw $t0, -44($fp)
	addi $sp, $sp, -4
	sub $t0, $fp, $sp
	sw $t0, 0($sp)
	addi $sp, $sp, -8
	lw $a0, -44($fp)
	sw $a0, -0($sp)
	add $fp, $sp, $0
	jal Label_malloc
	sw $v0, -48($fp)
Label_23:
	lw $t1, -48($fp)
	li $t2, 0
	add $t0, $t1, $t2
	sw $t0, -8($fp)
	li $t1, 0
	li $t2, 0
	add $t0, $t1, $t2
	sw $t0, -20($fp)
Label_24:
	lw $t1, -20($fp)
	lw $t2, 0($fp)
	slt $t0, $t1, $t2
	sw $t0, -52($fp)
	lw $t0, -52($fp)
	beqz $t0, Label_25
	li $t1, 0
	li $t2, 0
	add $t0, $t1, $t2
	sw $t0, -56($fp)
	li $t1, 0
	li $t2, 0
	add $t0, $t1, $t2
	lw $t3, -56($fp)
	sub $t3, $fp, $t3
	sw $t0, -8($t3)
Label_26:
	lw $t1, -20($fp)
	li $t2, 1
	add $t0, $t1, $t2
	sw $t0, -60($fp)
	lw $t1, -60($fp)
	li $t2, 0
	add $t0, $t1, $t2
	sw $t0, -20($fp)
	j Label_24
Label_25:
	li $t1, 2
	lw $t2, 0($fp)
	mul $t0, $t1, $t2
	sw $t0, -64($fp)
	lw $t1, -64($fp)
	li $t2, 4
	mul $t0, $t1, $t2
	sw $t0, -64($fp)
	addi $sp, $sp, -4
	sub $t0, $fp, $sp
	sw $t0, 0($sp)
	addi $sp, $sp, -8
	lw $a0, -64($fp)
	sw $a0, -0($sp)
	add $fp, $sp, $0
	jal Label_malloc
	sw $v0, -68($fp)
Label_27:
	lw $t1, -68($fp)
	li $t2, 0
	add $t0, $t1, $t2
	sw $t0, -12($fp)
	li $t1, 0
	li $t2, 0
	add $t0, $t1, $t2
	sw $t0, -20($fp)
Label_28:
	li $t1, 2
	lw $t2, 0($fp)
	mul $t0, $t1, $t2
	sw $t0, -72($fp)
	lw $t1, -20($fp)
	lw $t2, -72($fp)
	slt $t0, $t1, $t2
	sw $t0, -76($fp)
	lw $t0, -76($fp)
	beqz $t0, Label_29
	li $t1, 0
	li $t2, 0
	add $t0, $t1, $t2
	sw $t0, -80($fp)
	li $t1, 0
	li $t2, 0
	add $t0, $t1, $t2
	lw $t3, -80($fp)
	sub $t3, $fp, $t3
	sw $t0, -12($t3)
Label_30:
	lw $t1, -20($fp)
	li $t2, 1
	add $t0, $t1, $t2
	sw $t0, -84($fp)
	lw $t1, -84($fp)
	li $t2, 0
	add $t0, $t1, $t2
	sw $t0, -20($fp)
	j Label_28
Label_29:
	li $t1, 0
	li $t2, 0
	add $t0, $t1, $t2
	sw $t0, -16($fp)
	li $t1, 0
	li $t2, 0
	add $t0, $t1, $t2
	sw $t0, -20($fp)
Label_31:
	lw $t1, -20($fp)
	lw $t2, 0($fp)
	slt $t0, $t1, $t2
	sw $t0, -88($fp)
	lw $t0, -88($fp)
	beqz $t0, Label_32
	lw $t1, -20($fp)
	li $t2, 9
	slt $t0, $t1, $t2
	sw $t0, -92($fp)
	lw $t0, -92($fp)
	beqz $t0, Label_34
	lw $t1, -20($fp)
	li $t2, 1
	add $t0, $t1, $t2
	sw $t0, -96($fp)
	li $t1, 0
	li $t2, 0
	add $t0, $t1, $t2
	sw $t0, -100($fp)
	lw $t1, -96($fp)
	li $t2, 0
	add $t0, $t1, $t2
	lw $t3, -100($fp)
	sub $t3, $fp, $t3
	sw $t0, -4($t3)
	j Label_35
Label_34:
	lw $t1, -20($fp)
	li $t2, 9
	sub $t0, $t1, $t2
	sw $t0, -104($fp)
	li $t1, 0
	li $t2, 0
	add $t0, $t1, $t2
	sw $t0, -108($fp)
	lw $t1, -104($fp)
	li $t2, 0
	add $t0, $t1, $t2
	lw $t3, -108($fp)
	sub $t3, $fp, $t3
	sw $t0, -4($t3)
Label_35:
Label_33:
	lw $t1, -20($fp)
	li $t2, 1
	add $t0, $t1, $t2
	sw $t0, -112($fp)
	lw $t1, -112($fp)
	li $t2, 0
	add $t0, $t1, $t2
	sw $t0, -20($fp)
	j Label_31
Label_32:
	lw $t1, 0($fp)
	li $t2, 1
	sub $t0, $t1, $t2
	sw $t0, -116($fp)
	addi $sp, $sp, -4
	sub $t0, $fp, $sp
	sw $t0, 0($sp)
	addi $sp, $sp, -8
	lw $a0, -116($fp)
	sw $a0, -0($sp)
	lw $a1, -4($fp)
	sw $a1, -4($sp)
	add $fp, $sp, $0
	jal Label_printIntA
	sw $v0, -120($fp)
Label_36:
	li $t1, 0
	li $t2, 0
	add $t0, $t1, $t2
	sw $t0, -20($fp)
Label_37:
	lw $t1, -20($fp)
	lw $t2, 0($fp)
	slt $t0, $t1, $t2
	sw $t0, -124($fp)
	lw $t0, -124($fp)
	beqz $t0, Label_38
	lw $t1, 0($fp)
	li $t2, 2
	div $t0, $t1, $t2
	sw $t0, -128($fp)
	lw $t1, -20($fp)
	lw $t2, -128($fp)
	slt $t0, $t1, $t2
	sw $t0, -132($fp)
	lw $t0, -132($fp)
	beqz $t0, Label_40
	li $t1, 0
	li $t2, 0
	add $t0, $t1, $t2
	sw $t0, -136($fp)
	li $t1, 7
	li $t2, 0
	add $t0, $t1, $t2
	lw $t3, -136($fp)
	sub $t3, $fp, $t3
	sw $t0, -8($t3)
	j Label_41
Label_40:
	li $t1, 0
	li $t2, 0
	add $t0, $t1, $t2
	sw $t0, -140($fp)
	li $t1, 3
	li $t2, 0
	add $t0, $t1, $t2
	lw $t3, -140($fp)
	sub $t3, $fp, $t3
	sw $t0, -8($t3)
Label_41:
Label_39:
	lw $t1, -20($fp)
	li $t2, 1
	add $t0, $t1, $t2
	sw $t0, -144($fp)
	lw $t1, -144($fp)
	li $t2, 0
	add $t0, $t1, $t2
	sw $t0, -20($fp)
	j Label_37
Label_38:
	lw $t1, 0($fp)
	li $t2, 1
	sub $t0, $t1, $t2
	sw $t0, -148($fp)
	addi $sp, $sp, -4
	sub $t0, $fp, $sp
	sw $t0, 0($sp)
	addi $sp, $sp, -8
	lw $a0, -148($fp)
	sw $a0, -0($sp)
	lw $a1, -8($fp)
	sw $a1, -4($sp)
	add $fp, $sp, $0
	jal Label_printIntB
	sw $v0, -152($fp)
Label_42:
	addi $sp, $sp, -4
	sub $t0, $fp, $sp
	sw $t0, 0($sp)
	addi $sp, $sp, -8
	lw $a0, 0($fp)
	sw $a0, -0($sp)
	lw $a1, -4($fp)
	sw $a1, -4($sp)
	lw $a2, -8($fp)
	sw $a2, -8($sp)
	lw $a3, -12($fp)
	sw $a3, -12($sp)
	add $fp, $sp, $0
	jal Label_plus
	sw $v0, -156($fp)
Label_43:
	lw $t1, -156($fp)
	li $t2, 0
	add $t0, $t1, $t2
	sw $t0, -16($fp)
	addi $sp, $sp, -4
	sub $t0, $fp, $sp
	sw $t0, 0($sp)
	addi $sp, $sp, -8
	lw $a0, -16($fp)
	sw $a0, -0($sp)
	lw $a1, -12($fp)
	sw $a1, -4($sp)
	add $fp, $sp, $0
	jal Label_printBigInt
	sw $v0, -160($fp)
Label_44:
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

###########################################
Label_malloc:
	sw $31, 4($fp)
	addi $sp, $sp, -4
	
	li $v0, 9
	syscall
	
	addi $sp, $fp, 0
	lw $t0, 8($sp)
	lw $31, 4($sp)
	addi $sp, $sp, 8
	add $fp, $sp, $t0
	jr $31

#################################

.data
printf_buf: .space 2
	
