import React, { useState } from "react";
import { Link, Outlet, useLoaderData, useNavigate, useParams } from "react-router-dom";
import { api } from "./fetcher";

import { AppShell, Burger, Button, Center, Group, Select, Title } from '@mantine/core';
import { useDisclosure } from '@mantine/hooks';


type EncounterList = Array<{
    index: number,
    name: string,
    type: "TRASH" | "BOSS",
}>

export const rootLoader = api<EncounterList>(p => "/api/encounters")

export const RootPage = () => {
    const data = useLoaderData() as EncounterList;
    const routeParams = useParams();
    const navigate = useNavigate();

    const selectItems = data.filter(d => d.type === "BOSS").map(d => ({ value: d.index.toString(), label: d.name }))
    const onSelect = (value: string) => {
        value ? navigate(`/encounter/${value}`) : navigate(`/`)
    }

    return (
        <AppShell header={{ height: 60 }} padding="md">
            <AppShell.Header pl="md">
                <Group h="100%">
                    <Select
                        w="12rem"
                        value={routeParams.index}
                        data={selectItems}
                        onChange={onSelect}
                    />
                </Group>
            </AppShell.Header>
            <AppShell.Main>
                <Outlet />
            </AppShell.Main>
        </AppShell>
    );
}

