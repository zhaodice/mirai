/*
 * Copyright 2019-2021 Mamoe Technologies and contributors.
 *
 *  此源代码的使用受 GNU AFFERO GENERAL PUBLIC LICENSE version 3 许可证的约束, 可以在以下链接找到该许可证.
 *  Use of this source code is governed by the GNU AGPLv3 license that can be found through the following link.
 *
 *  https://github.com/mamoe/mirai/blob/master/LICENSE
 */

package net.mamoe.mirai.internal.network

import net.mamoe.mirai.internal.network.handler.components.ServerAddress
import net.mamoe.mirai.internal.network.handler.components.ServerList
import kotlin.test.*

internal class ServerListTest {

    @Test
    fun canInitializeDefaults() {
        assertNotEquals(0, ServerList.DefaultServerList.size)
    }

    @Test
    fun `can poll current for initial`() {
        assertNotNull(ServerList().pollCurrent())
    }

    @Test
    fun `not empty for initial`() {
        assertNotNull(ServerList().pollAny())
    }

    @Test
    fun `poll current will end with null`() {
        val instance = ServerList()
        repeat(100) {
            instance.pollCurrent()
        }
        assertNull(instance.pollCurrent())
    }

    @Test
    fun `poll any is always not null`() {
        val instance = ServerList()
        repeat(100) {
            instance.pollAny()
        }
        assertNotNull(instance.pollAny())
    }

    @Test
    fun `preferred cannot be empty`() {
        assertFailsWith<IllegalArgumentException> {
            ServerList().setPreferred(emptyList())
        }
    }

    @Test
    fun `use preferred`() {
        val instance = ServerList()
        val addr = ServerAddress("test", 1)
        instance.setPreferred(listOf(addr))
        repeat(100) {
            instance.pollAny()
        }
        assertSame(addr, instance.pollAny())
    }
}