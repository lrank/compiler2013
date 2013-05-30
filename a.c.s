.data
.globl str_0
	str_0: .asciiz "%d "
.globl str_1
	str_1: .asciiz "\n"
.align 2
.globl data_gp
	data_gp: .space 400
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
Label_main:
	sw $31, 4($fp)
	addi $sp, $sp, -36
	li $t1, 1
	li $t2, 0
	add $t0, $t1, $t2
	sw $t0, 0($fp)
Label_1:
	lw $t1, 0($fp)
	li $t2, 3
	slt $t0, $t1, $t2
	sw $t0, -8($fp)
	lw $t0, -8($fp)
	beqz $t0, Label_2
	li $t1, 1
	li $t2, 0
	add $t0, $t1, $t2
	sw $t0, -4($fp)
Label_4:
	lw $t1, -4($fp)
	li $t2, 3
	slt $t0, $t1, $t2
	sw $t0, -12($fp)
	lw $t0, -12($fp)
	beqz $t0, Label_5
	lw $t1, 0($fp)
	lw $t2, -4($fp)
	mul $t0, $t1, $t2
	sw $t0, -16($fp)
	li $t1, 0
	li $t2, 0
	add $t0, $t1, $t2
	sw $t0, -20($fp)
	lw $t1, -20($fp)
	lw $t2, 0($fp)
	add $t0, $t1, $t2
	sw $t0, -20($fp)
	lw $t1, -20($fp)
	li $t2, 10
	mul $t0, $t1, $t2
	sw $t0, -20($fp)
	lw $t1, -20($fp)
	lw $t2, -4($fp)
	add $t0, $t1, $t2
	sw $t0, -20($fp)
	lw $t1, -20($fp)
	li $t2, 4
	mul $t0, $t1, $t2
	sw $t0, -20($fp)
	lw $t1, -16($fp)
	li $t2, 0
	add $t0, $t1, $t2
	lw $t3, -20($fp)
	add $t3, $gp, $t3
	sw $t0, 0($t3)
	li $t1, 0
	li $t2, 0
	add $t0, $t1, $t2
	sw $t0, -24($fp)
	lw $t1, -24($fp)
	lw $t2, 0($fp)
	add $t0, $t1, $t2
	sw $t0, -24($fp)
	lw $t1, -24($fp)
	li $t2, 10
	mul $t0, $t1, $t2
	sw $t0, -24($fp)
	lw $t1, -24($fp)
	lw $t2, -4($fp)
	add $t0, $t1, $t2
	sw $t0, -24($fp)
	lw $t1, -24($fp)
	li $t2, 4
	mul $t0, $t1, $t2
	sw $t0, -24($fp)
	addi $sp, $sp, -4
	sub $t0, $fp, $sp
	sw $t0, 0($sp)
	addi $sp, $sp, -8
	la $a0, str_0
	sw $a0, -0($sp)
	lw $a1, -24($fp)
	add $a1, $gp, $a1
	lw $a1, 0($a1)
	sw $a1, -4($sp)
	add $fp, $sp, $0
	jal Label_printf
	sw $v0, -28($fp)
Label_7:
Label_6:
	lw $a1, -4($fp)
	addi $a1, $a1, 1
	sw $a1, -4($fp)
	j Label_4
Label_5:
	addi $sp, $sp, -4
	sub $t0, $fp, $sp
	sw $t0, 0($sp)
	addi $sp, $sp, -8
	la $a0, str_1
	sw $a0, -0($sp)
	add $fp, $sp, $0
	jal Label_printf
	sw $v0, -32($fp)
Label_8:
Label_3:
	lw $a1, 0($fp)
	addi $a1, $a1, 1
	sw $a1, 0($fp)
	j Label_1
Label_2:
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

printf_end:						#roll back
	addi $sp, $fp, 0
	lw $t0, 8($sp)
	lw $31, 4($sp)
	addi $sp, $sp, 8
	add $fp, $sp, $t0
	jr $31

.data
printf_buf: .space 2
	
